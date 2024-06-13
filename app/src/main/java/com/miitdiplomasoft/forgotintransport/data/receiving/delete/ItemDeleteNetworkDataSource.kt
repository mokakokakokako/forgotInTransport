package com.miitdiplomasoft.forgotintransport.data.receiving.delete

import com.miitdiplomasoft.forgotintransport.data.constants.Endpoint
import com.miitdiplomasoft.forgotintransport.data.registration.RegistrationDataSource
import io.ktor.client.HttpClient
import io.ktor.client.features.ClientRequestException
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.http.HttpStatusCode
import io.ktor.http.URLProtocol
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import timber.log.Timber

class ItemDeleteNetworkDataSource(
    private val host: String,
    private val httpClient: HttpClient,
    private val dispatcher: CoroutineDispatcher
): ItemDeleteDataSource {
    override suspend fun deleteItemById(id: Int): HttpStatusCode =
        withContext(dispatcher) {
            return@withContext try {
                httpClient.post<HttpStatusCode> {
                    url {
                        protocol = URLProtocol.HTTPS
                        host = this@ItemDeleteNetworkDataSource.host
                        path(Endpoint.deleteItem)
                        parameter("id", id)
                    }
                }.apply {
                    Timber.tag("EXCEPTION_TAG").d("Delete id: %s", this)
                }
            } catch (e: ClientRequestException) {
                Timber.tag("EXCEPTION_TAG").d("ItemDeleteNetworkDataSource: " + e)
                if (e.response.status == HttpStatusCode.NotFound) {
                    Timber.tag("EXCEPTION_TAG").d("Registration failed, login is already in use")
                }
                return@withContext HttpStatusCode.NotFound
            } catch (e: Exception) {
                Timber.tag("EXCEPTION_TAG").d("ItemDeleteNetworkDataSource exception: %s", e)
                return@withContext HttpStatusCode.BadRequest
            }
        }
}
