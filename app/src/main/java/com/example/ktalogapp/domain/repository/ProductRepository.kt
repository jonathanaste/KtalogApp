package com.example.ktalogapp.domain.repository

import com.example.ktalogapp.domain.model.Product

interface ProductRepository {
    suspend fun getProducts(): Result<List<Product>>
}