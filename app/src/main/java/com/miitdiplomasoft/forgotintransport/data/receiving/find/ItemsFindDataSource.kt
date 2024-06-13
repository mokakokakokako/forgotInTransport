package com.miitdiplomasoft.forgotintransport.data.receiving.find

import com.miitdiplomasoft.forgotintransport.model.response.MenuItemResponse

interface ItemsFindDataSource {
    suspend fun getItemsByName(token: String, path: String, name: String): ArrayList<MenuItemResponse>
}