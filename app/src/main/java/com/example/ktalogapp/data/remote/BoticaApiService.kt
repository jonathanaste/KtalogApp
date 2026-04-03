package com.example.ktalogapp.data.remote

import retrofit2.http.GET
import retrofit2.http.Query

interface BoticaApiService {
    @GET("products") // Tu endpoint mockeado en el server
    suspend fun getProducts(): List<ProductDTO>
    
    @GET("products/search")
    suspend fun searchProducts(@Query("q") query: String): List<ProductDTO>
}