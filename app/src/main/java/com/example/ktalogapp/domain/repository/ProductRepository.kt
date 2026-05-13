package com.example.ktalogapp.domain.repository

import com.example.ktalogapp.domain.model.Product

interface ProductRepository {
    suspend fun getProducts(): Result<List<Product>>
    suspend fun getProductById(id: String): Result<Product>
}
