package com.miitdiplomasoft.forgotintransport.data.receiving.single

import android.util.Log
import com.miitdiplomasoft.forgotintransport.data.constants.Endpoint
import com.miitdiplomasoft.forgotintransport.model.response.CardItemResponse
import io.ktor.client.HttpClient
import io.ktor.client.features.ClientRequestException
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.http.HttpStatusCode
import io.ktor.http.URLProtocol
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class ItemReceivingNetworkDataSource(
    private val host: String,
    private val httpClient: HttpClient,
    private val dispatcher: CoroutineDispatcher
): ItemReceivingDataSource {
    override suspend fun getItem(token: String, id: Int): CardItemResponse =
        withContext(dispatcher) {
            return@withContext try {
                httpClient.get<CardItemResponse>{
                    url{
                        protocol = URLProtocol.HTTPS
                        host = this@ItemReceivingNetworkDataSource.host
                        path(Endpoint.getItem)
                        parameter("token", token)
                        parameter("id", id)
                    }
                }.apply {
                    Log.d("EXCEPTION_TAG","$this")
                }
            } catch (e: ClientRequestException) {
                Log.d("EXCEPTION_TAG","ItemReceivingNetworkDataSource, error receiving: $e")
                if (e.response.status == HttpStatusCode.NotFound) {
                    Log.d("EXCEPTION_TAG","Not found")
                }
                return@withContext CardItemResponse(-1, "", false, "","", "")
            } catch (e: Exception) {
                Log.d("EXCEPTION_TAG","ItemReceivingNetworkDataSource exception: $e")
                return@withContext CardItemResponse(-1, "", false, "","", "")
            }
        }
}