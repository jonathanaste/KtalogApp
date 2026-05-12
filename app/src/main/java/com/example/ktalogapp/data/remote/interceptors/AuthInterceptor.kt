package com.example.ktalogapp.data.remote.interceptors

import okhttp3.Credentials
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(
    private val consumerKey: String,
    private val consumerSecret: String
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .addHeader("Authorization", Credentials.basic(consumerKey, consumerSecret))
            .build()
        return chain.proceed(request)
    }
}
