package com.miitdiplomasoft.forgotintransport.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.miitdiplomasoft.forgotintransport.data.receiving.add.ItemAddNetworkDataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddItemFragmentViewModel @Inject constructor(
    private val itemAddNetworkDataSource: ItemAddNetworkDataSource
): ViewModel() {

    private val _addResult = MutableLiveData<HttpStatusCode>()
    val addResult: LiveData<HttpStatusCode> = _addResult

    fun add(name: String, locationInfo: String, numbersInfo: String) {
        viewModelScope.launch {
            val result = itemAddNetworkDataSource.addItem(name, locationInfo, numbersInfo)
            _addResult.postValue(result)
        }
    }
}
