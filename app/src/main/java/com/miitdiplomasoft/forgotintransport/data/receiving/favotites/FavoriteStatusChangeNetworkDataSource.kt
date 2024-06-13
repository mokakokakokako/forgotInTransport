package com.miitdiplomasoft.forgotintransport.data.receiving.favotites

import com.miitdiplomasoft.forgotintransport.data.constants.Endpoint
import io.ktor.client.HttpClient
import io.ktor.client.features.ClientRequestException
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.http.HttpStatusCode
import io.ktor.http.URLProtocol
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import timber.log.Timber

class FavoriteStatusChangeNetworkDataSource(
    private val host: String,
    private val httpClient: HttpClient,
    private val dispatcher: CoroutineDispatcher
): FavoriteStatusChangeDataSource {
    override suspend fun changeFavoriteStatus(id: Int): HttpStatusCode =
        withContext(dispatcher) {
            return@withContext try {
                httpClient.post<HttpStatusCode> {
                    url {
                        protocol = URLProtocol.HTTPS
                        host = this@FavoriteStatusChangeNetworkDataSource.host
                        path(Endpoint.changeFavStatus)
                        parameter("id", id)
                    }
                }.apply {
                    Timber.tag("EXCEPTION_TAG").d("FavoriteStatus id: %s", this)
                }
            } catch (e: ClientRequestException) {
                Timber.tag("EXCEPTION_TAG").d("FavoriteStatusNetworkDataSource, error receiving: " + e)
                if (e.response.status == HttpStatusCode.NotFound) {
                    Timber.tag("EXCEPTION_TAG").d("FavoriteStatusChange failed")
                }
                return@withContext HttpStatusCode.NotFound
            } catch (e: Exception) {
                Timber.tag("EXCEPTION_TAG").d("FavoriteStatusNetworkDataSource exception: %s", e)
                return@withContext HttpStatusCode.BadRequest
            }
        }
}