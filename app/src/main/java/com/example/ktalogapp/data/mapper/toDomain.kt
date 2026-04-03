package com.example.ktalogapp.data.mapper

import com.example.ktalogapp.data.remote.ProductDTO
import com.example.ktalogapp.domain.model.Product

fun ProductDTO.toDomain(): Product {
    return Product(
        id = id,
        name = name,
        price = price,
        description = description,
        imageUrl = imageUrl,
        category = category,
        hasStock = stock > 0
    )
}