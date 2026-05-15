package com.example.ktalogapp.domain.usecase

import com.example.ktalogapp.domain.model.Product
import com.example.ktalogapp.domain.repository.ProductRepository
import javax.inject.Inject

class GetProductByIdUseCase @Inject constructor(
    private val repository: ProductRepository
) {
    suspend operator fun invoke(id: String): Result<Product> = repository.getProductById(id)
}
