package com.miitdiplomasoft.forgotintransport.data.receiving.add

import com.miitdiplomasoft.forgotintransport.data.constants.Endpoint
import com.miitdiplomasoft.forgotintransport.data.receiving.delete.ItemDeleteDataSource
import io.ktor.client.HttpClient
import io.ktor.client.features.ClientRequestException
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.http.HttpStatusCode
import io.ktor.http.URLProtocol
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import timber.log.Timber

class ItemAddNetworkDataSource(
    private val host: String,
    private val httpClient: HttpClient,
    private val dispatcher: CoroutineDispatcher
): ItemAddDataSource {
    override suspend fun addItem(name: String, locationInfo: String, numbersInfo: String): HttpStatusCode =
        withContext(dispatcher) {
            return@withContext try {
                httpClient.post<HttpStatusCode> {
                    url {
                        protocol = URLProtocol.HTTPS
                        host = this@ItemAddNetworkDataSource.host
                        path(Endpoint.addItem)
                        parameter("name", name)
                        parameter("locationInfo", locationInfo)
                        parameter("numbersInfo", numbersInfo)
                    }
                }.apply {
                    Timber.tag("EXCEPTION_TAG").d("Delete id: %s", this)
                }
            } catch (e: ClientRequestException) {
                Timber.tag("EXCEPTION_TAG").d("ItemAddNetworkDataSource: " + e)
                if (e.response.status == HttpStatusCode.NotFound) {
                    Timber.tag("EXCEPTION_TAG").d("Not found")
                }
                return@withContext HttpStatusCode.NotFound
            } catch (e: Exception) {
                Timber.tag("EXCEPTION_TAG").d("ItemAddNetworkDataSource exception: %s", e)
                return@withContext HttpStatusCode.BadRequest
            }
        }
}
