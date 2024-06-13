package com.miitdiplomasoft.forgotintransport.di

import com.miitdiplomasoft.forgotintransport.data.authorization.AuthorizationNetworkDataSource
import com.miitdiplomasoft.forgotintransport.data.constants.Endpoint
import com.miitdiplomasoft.forgotintransport.data.receiving.add.ItemAddNetworkDataSource
import com.miitdiplomasoft.forgotintransport.data.receiving.delete.ItemDeleteNetworkDataSource
import com.miitdiplomasoft.forgotintransport.data.receiving.favotites.FavoriteStatusChangeNetworkDataSource
import com.miitdiplomasoft.forgotintransport.data.receiving.find.ItemsFindNetworkDataSource
import com.miitdiplomasoft.forgotintransport.data.receiving.many.ItemsReceivingNetworkDataSource
import com.miitdiplomasoft.forgotintransport.data.receiving.single.ItemReceivingNetworkDataSource
import com.miitdiplomasoft.forgotintransport.data.registration.RegistrationNetworkDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.features.HttpTimeout
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.client.features.logging.DEFAULT
import io.ktor.client.features.logging.LogLevel
import io.ktor.client.features.logging.Logger
import io.ktor.client.features.logging.Logging
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.serialization.json.Json
import javax.inject.Singleton
import kotlin.time.Duration.Companion.seconds

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    fun provideJson(): Json {
        return Json {
            isLenient = true
            ignoreUnknownKeys = true
        }
    }
    @Provides
    @Singleton
    fun provideHttpClient(json: Json): HttpClient {
        return HttpClient(OkHttp) {
            install(JsonFeature) {
                serializer = KotlinxSerializer(json)
            }
            install(Logging) {
                logger = Logger.DEFAULT
                level = LogLevel.INFO
            }
            install(HttpTimeout) {
                connectTimeoutMillis = 20.seconds.inWholeMilliseconds
                requestTimeoutMillis = 60.seconds.inWholeMilliseconds
                socketTimeoutMillis = 20.seconds.inWholeMilliseconds
            }
        }
    }
    @Provides
    @Singleton
    fun providesHost(): String = Endpoint.baseURL
    @Provides
    @Singleton
    fun provideAuthorizationNetworkDataSource(host: String, httpClient: HttpClient, @Dispatcher(Dispatcher.IO) dispatcher: CoroutineDispatcher): AuthorizationNetworkDataSource {
        return AuthorizationNetworkDataSource(host, httpClient, dispatcher)
    }
    @Provides
    @Singleton
    fun provideRegistrationNetworkDataSource(host: String, httpClient: HttpClient, @Dispatcher(Dispatcher.IO) dispatcher: CoroutineDispatcher): RegistrationNetworkDataSource {
        return RegistrationNetworkDataSource(host, httpClient, dispatcher)
    }
    @Provides
    @Singleton
    fun provideItemsReceivingNetworkDataSource(host: String, httpClient: HttpClient, @Dispatcher(Dispatcher.IO) dispatcher: CoroutineDispatcher): ItemsReceivingNetworkDataSource {
        return ItemsReceivingNetworkDataSource(host, httpClient, dispatcher)
    }
    @Provides
    @Singleton
    fun provideItemReceivingNetworkDataSource(host: String, httpClient: HttpClient, @Dispatcher(Dispatcher.IO) dispatcher: CoroutineDispatcher): ItemReceivingNetworkDataSource {
        return ItemReceivingNetworkDataSource(host, httpClient, dispatcher)
    }
    @Provides
    @Singleton
    fun provideFavoriteStatusChangeNetworkDataSource(host: String, httpClient: HttpClient, @Dispatcher(Dispatcher.IO) dispatcher: CoroutineDispatcher): FavoriteStatusChangeNetworkDataSource {
        return FavoriteStatusChangeNetworkDataSource(host, httpClient, dispatcher)
    }
    @Provides
    @Singleton
    fun provideItemsFindNetworkDataSource(host: String, httpClient: HttpClient, @Dispatcher(Dispatcher.IO) dispatcher: CoroutineDispatcher): ItemsFindNetworkDataSource {
        return ItemsFindNetworkDataSource(host, httpClient, dispatcher)
    }
    @Provides
    @Singleton
    fun provideItemDeleteNetworkDataSource(host: String, httpClient: HttpClient, @Dispatcher(Dispatcher.IO) dispatcher: CoroutineDispatcher): ItemDeleteNetworkDataSource {
        return ItemDeleteNetworkDataSource(host, httpClient, dispatcher)
    }
    @Provides
    @Singleton
    fun provideItemAddNetworkDataSource(host: String, httpClient: HttpClient, @Dispatcher(Dispatcher.IO) dispatcher: CoroutineDispatcher): ItemAddNetworkDataSource {
        return ItemAddNetworkDataSource(host, httpClient, dispatcher)
    }
}

