package com.miitdiplomasoft.forgotintransport.model.response

import kotlinx.serialization.Serializable

@Serializable
data class MenuItemResponse(
    var id: Int? = null,
    var name: String? = null,
    var imageUrl: Int? = null,
)
