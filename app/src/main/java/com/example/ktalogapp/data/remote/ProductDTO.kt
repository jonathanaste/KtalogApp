package com.example.ktalogapp.data.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProductDTO(
    @SerialName("id") val id: Int,
    @SerialName("name") val name: String,
    @SerialName("price") val price: String,
    @SerialName("description") val description: String,
    @SerialName("short_description") val shortDescription: String = "",
    @SerialName("images") val images: List<ImageDTO> = emptyList(),
    @SerialName("categories") val categories: List<CategoryDTO> = emptyList(),
    @SerialName("stock_status") val stockStatus: String = "instock",
    @SerialName("stock_quantity") val stockQuantity: Int? = 0
)

@Serializable
data class ImageDTO(
    @SerialName("src") val src: String
)

@Serializable
data class CategoryDTO(
    @SerialName("name") val name: String
)
