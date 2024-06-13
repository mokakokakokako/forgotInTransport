package com.miitdiplomasoft.forgotintransport.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.miitdiplomasoft.forgotintransport.data.constants.Endpoint
import com.miitdiplomasoft.forgotintransport.data.receiving.find.ItemsFindNetworkDataSource
import com.miitdiplomasoft.forgotintransport.model.response.MenuItemResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchFragmentViewModel @Inject constructor(
    private val itemsFindNetworkDataSource: ItemsFindNetworkDataSource
): ViewModel() {
    private val _items = MutableLiveData<ArrayList<MenuItemResponse>>()
    val items: LiveData<ArrayList<MenuItemResponse>> = _items

    fun getNamItems(token: String, name: String) {
        viewModelScope.launch {
            val response = itemsFindNetworkDataSource.getItemsByName(token, Endpoint.getNamItems, name)
            _items.postValue(response)
        }
    }
}
