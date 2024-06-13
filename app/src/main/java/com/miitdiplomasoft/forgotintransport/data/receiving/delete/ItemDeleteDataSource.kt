package com.miitdiplomasoft.forgotintransport.data.receiving.delete

import io.ktor.http.HttpStatusCode

interface ItemDeleteDataSource {
    suspend fun deleteItemById(id: Int): HttpStatusCode
}