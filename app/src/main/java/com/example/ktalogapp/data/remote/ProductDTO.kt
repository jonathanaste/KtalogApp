package com.example.ktalogapp.data.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProductDTO(
    @SerialName("id") val id: String,
    @SerialName("name") val name: String,
    @SerialName("price") val price: Double,
    @SerialName("description") val description: String,
    @SerialName("image_url") val imageUrl: String,
    @SerialName("category") val category: String,
    @SerialName("stock") val stock: Int
)