package com.miitdiplomasoft.forgotintransport.model.response

import kotlinx.serialization.Serializable

@Serializable
data class CardItemResponse(
    val id: Int? = null,
    val name: String? = null,
    val isLiked: Boolean? = null,
    val imageUrl: String? = null,
    val locationInfo: String? = null,
    val numbersInfo: String? = null,
)