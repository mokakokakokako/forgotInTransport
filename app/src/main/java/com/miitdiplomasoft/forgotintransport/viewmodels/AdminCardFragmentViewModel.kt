package com.miitdiplomasoft.forgotintransport.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.miitdiplomasoft.forgotintransport.data.receiving.delete.ItemDeleteNetworkDataSource
import com.miitdiplomasoft.forgotintransport.data.receiving.single.ItemReceivingNetworkDataSource
import com.miitdiplomasoft.forgotintransport.model.response.CardItemResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AdminCardFragmentViewModel @Inject constructor(
    private val itemDeleteNetworkDataSource: ItemDeleteNetworkDataSource,
    private val itemReceivingNetworkDataSource: ItemReceivingNetworkDataSource
): ViewModel() {

    private val _deleteResult = MutableLiveData<HttpStatusCode>()
    val deleteResult: LiveData<HttpStatusCode> = _deleteResult

    fun delete(id: Int) {
        viewModelScope.launch {
            val result = itemDeleteNetworkDataSource.deleteItemById(id)
            _deleteResult.postValue(result)
        }
    }

    private val _item = MutableLiveData<CardItemResponse>()
    val item: LiveData<CardItemResponse> = _item

    fun getItem(token: String, id: Int) {
        viewModelScope.launch {
            val result = itemReceivingNetworkDataSource.getItem(token, id)
            _item.postValue(result)
        }
    }
}
