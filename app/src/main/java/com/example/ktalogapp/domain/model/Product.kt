package com.example.ktalogapp.domain.model

data class Product(
    val id: String,
    val name: String,
    val price: Double,
    val description: String,
    val shortDescription: String = "",
    val imageUrl: String,
    val images: List<String> = emptyList(),
    val category: String,
    val hasStock: Boolean
)
