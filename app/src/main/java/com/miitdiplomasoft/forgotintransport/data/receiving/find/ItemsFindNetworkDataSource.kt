package com.miitdiplomasoft.forgotintransport.data.receiving.find

import android.util.Log
import com.miitdiplomasoft.forgotintransport.data.receiving.many.ItemsReceivingDataSource
import com.miitdiplomasoft.forgotintransport.model.response.MenuItemResponse
import io.ktor.client.HttpClient
import io.ktor.client.features.ClientRequestException
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.http.HttpStatusCode
import io.ktor.http.URLProtocol
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class ItemsFindNetworkDataSource(
    private val host: String,
    private val httpClient: HttpClient,
    private val dispatcher: CoroutineDispatcher
): ItemsFindDataSource {
    override suspend fun getItemsByName(token: String, path: String, name: String): ArrayList<MenuItemResponse> =
        withContext(dispatcher) {
            return@withContext try {
                httpClient.get<ArrayList<MenuItemResponse>>{
                    url{
                        protocol = URLProtocol.HTTPS
                        host = this@ItemsFindNetworkDataSource.host
                        path(path)
                        parameter("token", token)
                        parameter("name", name)
                    }
                }.apply {
                    Log.d("EXCEPTION_TAG","$this")
                }
            } catch (e: ClientRequestException) {
                Log.d("EXCEPTION_TAG","ItemsFindNetworkDataSource, error receiving: $e")
                if (e.response.status == HttpStatusCode.NotFound) {
                    Log.d("EXCEPTION_TAG","Not found")
                }
                return@withContext arrayListOf()
            } catch (e: Exception) {
                Log.d("EXCEPTION_TAG","ItemsFindNetworkDataSource exception: $e")
                return@withContext arrayListOf()
            }
        }
}