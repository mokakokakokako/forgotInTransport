package com.miitdiplomasoft.forgotintransport.data.authorization

interface AuthorizationDataSource {
    suspend fun authorizationWithLoginAndPassword(login: String, password: String): String

    suspend fun authorizationWithLoginPasswordCode(login: String, password: String, code: String): String
}
