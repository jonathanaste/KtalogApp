package com.example.ktalogapp.data.repositoryImpl

import com.example.ktalogapp.data.mapper.toDomain
import com.example.ktalogapp.data.remote.BoticaApiService
import com.example.ktalogapp.domain.model.Product
import com.example.ktalogapp.domain.repository.ProductRepository
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val api: BoticaApiService
) : ProductRepository {

    override suspend fun getProducts(): Result<List<Product>> {
        return try {
            val response = api.getProducts()
            Result.success(response.map { it.toDomain() })
        } catch (e: Exception) {
            // Aquí podrías loguear el error o mapearlo a algo más específico
            Result.failure(e)
        }
    }
}