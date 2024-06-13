package com.miitdiplomasoft.forgotintransport.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.miitdiplomasoft.forgotintransport.data.receiving.favotites.FavoriteStatusChangeNetworkDataSource
import com.miitdiplomasoft.forgotintransport.data.receiving.single.ItemReceivingNetworkDataSource
import com.miitdiplomasoft.forgotintransport.model.response.CardItemResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class CardFragmentViewModel @Inject constructor(
    private val favoriteStatusChangeNetworkDataSource: FavoriteStatusChangeNetworkDataSource,
    private val itemReceivingNetworkDataSource: ItemReceivingNetworkDataSource
): ViewModel() {

    private val _favoriteStatusChangeResult = MutableLiveData<HttpStatusCode>()
    val favoriteStatusChangeResult: LiveData<HttpStatusCode> = _favoriteStatusChangeResult

    fun changeFavoriteStatus(id: Int) {
        viewModelScope.launch {
            val result = favoriteStatusChangeNetworkDataSource.changeFavoriteStatus(id)
            _favoriteStatusChangeResult.postValue(result)
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