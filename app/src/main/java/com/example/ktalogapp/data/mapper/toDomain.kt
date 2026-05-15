package com.example.ktalogapp.data.mapper

import com.example.ktalogapp.data.remote.ProductDTO
import com.example.ktalogapp.domain.model.Product

fun ProductDTO.toDomain(): Product {
    return Product(
        id = id.toString(),
        name = name,
        price = price.toDoubleOrNull() ?: 0.0,
        description = description,
        shortDescription = shortDescription,
        imageUrl = images.firstOrNull()?.src ?: "",
        images = images.map { it.src },
        category = categories.firstOrNull()?.name ?: "General",
        hasStock = stockStatus == "instock"
    )
}
