package com.example.ktalogapp.data.repositoryImpl

import com.example.ktalogapp.data.mapper.toDomain
import com.example.ktalogapp.data.remote.BoticaApiService
import com.example.ktalogapp.domain.model.Product
import com.example.ktalogapp.domain.repository.ProductRepository
import java.net.UnknownHostException
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val api: BoticaApiService
) : ProductRepository {

    override suspend fun getProducts(): Result<List<Product>> {
        return try {
            val response = api.getProducts()
            Result.success(response.map { it.toDomain() })
        } catch (e: UnknownHostException) {
            Result.failure(Exception("Error de conexión: No se pudo encontrar el servidor de La Botica. Revisa el internet de tu emulador."))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getProductById(id: String): Result<Product> {
        return try {
            val response = api.getProductById(id)
            Result.success(response.toDomain())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
