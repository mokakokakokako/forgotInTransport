package com.miitdiplomasoft.forgotintransport.data.registration

import io.ktor.http.HttpStatusCode

interface RegistrationDataSource {
    suspend fun registrationWithLoginAndPassword(login: String, password: String): HttpStatusCode
}