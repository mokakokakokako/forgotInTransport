package com.miitdiplomasoft.forgotintransport.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.miitdiplomasoft.forgotintransport.data.constants.Endpoint
import com.miitdiplomasoft.forgotintransport.data.receiving.many.ItemsReceivingNetworkDataSource
import com.miitdiplomasoft.forgotintransport.model.response.MenuItemResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MenuFragmentViewModel @Inject constructor(
    private val itemsReceivingNetworkDataSource: ItemsReceivingNetworkDataSource
): ViewModel() {
    private val _items = MutableLiveData<ArrayList<MenuItemResponse>>()
    val items: LiveData<ArrayList<MenuItemResponse>> = _items

    fun getAllItems(token: String) {
        viewModelScope.launch {
            val response = itemsReceivingNetworkDataSource.getItems()
            _items.postValue(response)
        }
    }
}
