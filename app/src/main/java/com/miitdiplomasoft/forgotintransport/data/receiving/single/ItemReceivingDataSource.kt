package com.miitdiplomasoft.forgotintransport.data.receiving.single

import com.miitdiplomasoft.forgotintransport.model.response.CardItemResponse

interface ItemReceivingDataSource {
    suspend fun getItem(token: String, id: Int): CardItemResponse
}