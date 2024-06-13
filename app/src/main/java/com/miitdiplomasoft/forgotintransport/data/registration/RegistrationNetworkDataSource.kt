package com.miitdiplomasoft.forgotintransport.data.registration

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

class RegistrationNetworkDataSource(
    private val host: String,
    private val httpClient: HttpClient,
    private val dispatcher: CoroutineDispatcher
): RegistrationDataSource {
    override suspend fun registrationWithLoginAndPassword(login: String, password: String): HttpStatusCode =
        withContext(dispatcher) {
            return@withContext try {
                httpClient.post<HttpStatusCode> {
                    url {
                        protocol = URLProtocol.HTTPS
                        host = this@RegistrationNetworkDataSource.host
                        path(Endpoint.registration)
                        parameter("login", login)
                        parameter("password", password)
                    }
                }.apply {
                    Timber.tag("EXCEPTION_TAG").d("Registration id: %s", this)
                }
            } catch (e: ClientRequestException) {
                Timber.tag("EXCEPTION_TAG").d("RegistrationNetworkDataSource, error registration: " + e)
                if (e.response.status == HttpStatusCode.NotFound) {
                    Timber.tag("EXCEPTION_TAG").d("Registration failed, login is already in use")
                }
                return@withContext HttpStatusCode.NotFound
            } catch (e: Exception) {
                Timber.tag("EXCEPTION_TAG").d("RegistrationNetworkDataSource exception: %s", e)
                return@withContext HttpStatusCode.BadRequest
            }
        }
}
