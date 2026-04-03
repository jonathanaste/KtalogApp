package com.example.ktalogapp.data.remote.di

import com.example.ktalogapp.data.remote.BoticaApiService
import com.example.ktalogapp.data.remote.interceptors.MockInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    private const val IS_MOCK_MODE = true
    @Provides
    @Singleton
    fun provideJson(): Json {
        return Json {
            ignoreUnknownKeys = true
            coerceInputValues = true
        }
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val builder = OkHttpClient.Builder()

        if (IS_MOCK_MODE) {
            builder.addInterceptor(MockInterceptor())
        }

        return builder.build()
    }
    
    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient,json: Json): Retrofit {
        val contentType = "application/json".toMediaType()
        return Retrofit.Builder().baseUrl("http://tiendalabotica.com")
            .client(okHttpClient)
            .addConverterFactory(json.asConverterFactory(contentType))
            .build()
    }

    @Provides
    @Singleton
    fun provideBoticaApiService(retrofit: Retrofit): BoticaApiService {
        return retrofit.create(BoticaApiService::class.java)
    }
}