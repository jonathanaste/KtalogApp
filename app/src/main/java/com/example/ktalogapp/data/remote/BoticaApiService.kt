package com.example.ktalogapp.data.remote

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface BoticaApiService {
    
    @GET("products")
    suspend fun getProducts(
        @Query("per_page") perPage: Int = 50,
        @Query("status") status: String = "publish"
    ): List<ProductDTO>
    
    @GET("products")
    suspend fun searchProducts(
        @Query("search") query: String,
        @Query("per_page") perPage: Int = 50
    ): List<ProductDTO>

    @GET("products/{id}")
    suspend fun getProductById(
        @Path("id") id: String
    ): ProductDTO
}
