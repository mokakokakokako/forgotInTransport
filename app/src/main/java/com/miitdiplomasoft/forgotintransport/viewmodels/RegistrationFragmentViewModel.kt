package com.miitdiplomasoft.forgotintransport.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.miitdiplomasoft.forgotintransport.data.registration.RegistrationNetworkDataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegistrationFragmentViewModel @Inject constructor(
    private val registrationNetworkDataSource: RegistrationNetworkDataSource
): ViewModel() {

    private val _registrationResult = MutableLiveData<HttpStatusCode>()
    val registrationResult: LiveData<HttpStatusCode> = _registrationResult

    fun register(login: String, password: String) {
        viewModelScope.launch {
            val result = registrationNetworkDataSource.registrationWithLoginAndPassword(login, password)
            _registrationResult.postValue(result)
        }
    }
}