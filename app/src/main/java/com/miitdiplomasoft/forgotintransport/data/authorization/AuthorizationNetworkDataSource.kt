package com.miitdiplomasoft.forgotintransport.data.authorization

import com.miitdiplomasoft.forgotintransport.data.constants.Endpoint
import io.ktor.client.HttpClient
import io.ktor.client.features.ClientRequestException
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.http.HttpStatusCode
import io.ktor.http.URLProtocol
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import timber.log.Timber

class AuthorizationNetworkDataSource(
    private val host: String,
    private val httpClient: HttpClient,
    private val dispatcher: CoroutineDispatcher
): AuthorizationDataSource {
    override suspend fun authorizationWithLoginAndPassword(login: String, password: String): String =
        withContext(dispatcher) {
            return@withContext try {
                httpClient.get<String> {
                    url{
                        protocol = URLProtocol.HTTPS
                        host = this@AuthorizationNetworkDataSource.host
                        path(Endpoint.authorizationUser)
                        parameter("login", login)
                        parameter("password", password)
                    }
                }.apply {
                    Timber.tag("EXCEPTION_TAG").d("Authorization id: %s", this)
                }
            } catch (e: ClientRequestException) {
                Timber.tag("EXCEPTION_TAG").d("AuthorizationNetworkDataSource, error autorization: " + e)
                if (e.response.status == HttpStatusCode.NotFound) {
                    Timber.tag("EXCEPTION_TAG").d("Authorization data not found")
                }
                return@withContext "-1"
            } catch (e: Exception) {
                Timber.tag("EXCEPTION_TAG").d("AuthorizationNetworkDataSource exception: %s", e)
                return@withContext "-1"
            }
        }

    override suspend fun authorizationWithLoginPasswordCode(login: String, password: String, code: String): String =
        withContext(dispatcher) {
            return@withContext try {
                httpClient.get<String> {
                    url{
                        protocol = URLProtocol.HTTPS
                        host = this@AuthorizationNetworkDataSource.host
                        path(Endpoint.authorizationAdmin)
                        parameter("login", login)
                        parameter("password", password)
                        parameter("code", code)

                    }
                }.apply {
                    Timber.tag("EXCEPTION_TAG").d("Authorization id: %s", this)
                }
            } catch (e: ClientRequestException) {
                Timber.tag("EXCEPTION_TAG").d("AuthorizationNetworkDataSource, error autorization: " + e)
                if (e.response.status == HttpStatusCode.NotFound) {
                    Timber.tag("EXCEPTION_TAG").d("Authorization data not found")
                }
                return@withContext "-1"
            } catch (e: Exception) {
                Timber.tag("EXCEPTION_TAG").d("AuthorizationNetworkDataSource exception: %s", e)
                return@withContext "-1"
            }
        }

}

