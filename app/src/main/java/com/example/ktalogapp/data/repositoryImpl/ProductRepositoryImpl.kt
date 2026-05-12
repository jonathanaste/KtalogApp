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
            Result.failure(Exception("No se pudo resolver el servidor (tiendalabotica.com). Revisa tu conexión a internet."))
        } catch (e: Exception) {
            Result.failure(Exception("Error al conectar con la tienda: ${e.localizedMessage}"))
        }
    }
}
