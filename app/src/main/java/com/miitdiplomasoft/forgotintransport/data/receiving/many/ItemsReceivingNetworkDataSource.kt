package com.miitdiplomasoft.forgotintransport.data.receiving.many

import android.util.Log
import com.miitdiplomasoft.forgotintransport.data.constants.Endpoint
import com.miitdiplomasoft.forgotintransport.model.response.MenuItemResponse
import io.ktor.client.HttpClient
import io.ktor.client.features.ClientRequestException
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.http.HttpStatusCode
import io.ktor.http.URLProtocol
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class ItemsReceivingNetworkDataSource(
    private val host: String,
    private val httpClient: HttpClient,
    private val dispatcher: CoroutineDispatcher
): ItemsReceivingDataSource {
    override suspend fun getItems(): ArrayList<MenuItemResponse> =
        withContext(dispatcher) {
            return@withContext try {
                httpClient.get<ArrayList<MenuItemResponse>>{
                    url{
                        protocol = URLProtocol.HTTPS
                        host = this@ItemsReceivingNetworkDataSource.host
                        path(Endpoint.getAllItems)
                    }
                }.apply {
                    Log.d("EXCEPTION_TAG","$this")
                }
            } catch (e: ClientRequestException) {
                Log.d("EXCEPTION_TAG","ItemsReceivingNetworkDataSource, error receiving: $e")
                if (e.response.status == HttpStatusCode.NotFound) {
                    Log.d("EXCEPTION_TAG","Not found")
                }
                return@withContext arrayListOf()
            } catch (e: Exception) {
                Log.d("EXCEPTION_TAG","ItemsReceivingNetworkDataSource exception: $e")
                return@withContext arrayListOf()
            }
        }
}

