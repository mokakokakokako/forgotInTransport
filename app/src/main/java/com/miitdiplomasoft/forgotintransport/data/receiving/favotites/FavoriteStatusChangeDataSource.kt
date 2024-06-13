package com.miitdiplomasoft.forgotintransport.data.receiving.favotites

import io.ktor.http.HttpStatusCode

interface FavoriteStatusChangeDataSource {
    suspend fun changeFavoriteStatus(id: Int): HttpStatusCode
}