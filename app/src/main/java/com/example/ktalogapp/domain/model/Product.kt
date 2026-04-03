package com.example.ktalogapp.domain.model

data class Product(
    val id: String,
    val name: String,
    val price: Double,
    val description: String,
    val imageUrl: String,
    val category: String,
    val hasStock: Boolean
)