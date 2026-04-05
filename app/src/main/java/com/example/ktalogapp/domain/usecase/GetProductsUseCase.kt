package com.example.ktalogapp.domain.usecase

import com.example.ktalogapp.domain.model.Product
import com.example.ktalogapp.domain.repository.ProductRepository
import com.example.ktalogapp.ui.catalog.SortOrder
import javax.inject.Inject

class GetProductsUseCase @Inject constructor(
    private val repository: ProductRepository
) {
    suspend operator fun invoke(
        query: String = "",
        sortOrder: SortOrder = SortOrder.NAME_ASC
    ): Result<List<Product>> {
        return repository.getProducts().map { list ->
            list.filter { 
                it.name.contains(query, ignoreCase = true) || 
                it.description.contains(query, ignoreCase = true) 
            }.let { filtered ->
                when (sortOrder) {
                    SortOrder.NAME_ASC -> filtered.sortedBy { it.name }
                    SortOrder.PRICE_ASC -> filtered.sortedBy { it.price }
                    SortOrder.PRICE_DESC -> filtered.sortedByDescending { it.price }
                }
            }
        }
    }
}