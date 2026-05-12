package com.example.ktalogapp.data.remote

import retrofit2.http.GET
import retrofit2.http.Query

interface BoticaApiService {
    
    // WooCommerce usa el parámetro 'per_page' para la cantidad de productos
    @GET("products")
    suspend fun getProducts(
        @Query("per_page") perPage: Int = 50,
        @Query("status") status: String = "publish"
    ): List<ProductDTO>
    
    // En WooCommerce la búsqueda se hace en el mismo endpoint con el parámetro 'search'
    @GET("products")
    suspend fun searchProducts(
        @Query("search") query: String,
        @Query("per_page") perPage: Int = 50
    ): List<ProductDTO>
}
