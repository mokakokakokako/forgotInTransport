package com.miitdiplomasoft.forgotintransport.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.miitdiplomasoft.forgotintransport.data.authorization.AuthorizationNetworkDataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthorizationFragmentViewModel @Inject constructor(
    private val authorizationNetworkDataSource: AuthorizationNetworkDataSource,
): ViewModel() {

    private val _authorizationResultUser = MutableLiveData<String>()
    val authorizationResultUser: LiveData<String> = _authorizationResultUser

    fun authorizeAsUser(login: String, password: String) {
        viewModelScope.launch {
            val result = authorizationNetworkDataSource.authorizationWithLoginAndPassword(login, password)
            _authorizationResultUser.postValue(result)
        }
    }

    private val _authorizationResultAdmin = MutableLiveData<String>()
    val authorizationResultAdmin: LiveData<String> = _authorizationResultAdmin

    fun authorizeAsAdmin(login: String, password: String, code: String) {
        viewModelScope.launch {
            val result = authorizationNetworkDataSource.authorizationWithLoginPasswordCode(login, password, code)
            _authorizationResultAdmin.postValue(result)
        }
    }
}
