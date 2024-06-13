package com.miitdiplomasoft.forgotintransport.data.receiving.add

import io.ktor.http.HttpStatusCode

interface ItemAddDataSource {
    suspend fun addItem(name: String, locationInfo: String, numbersInfo: String): HttpStatusCode
}