package com.miitdiplomasoft.forgotintransport.data.receiving.many

import com.miitdiplomasoft.forgotintransport.model.response.MenuItemResponse

interface ItemsReceivingDataSource {
    suspend fun getItems(): ArrayList<MenuItemResponse>
}