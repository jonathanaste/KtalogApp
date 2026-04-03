package com.example.ktalogapp.ui.catalog

import com.example.ktalogapp.domain.model.Product

sealed class CatalogContract {

    /** ACTIONS */
    sealed class Event {
        object LoadProducts : Event()
        data class OnSearchQueryChanged(val query: String) : Event()
        data class OnSortOrderChanged(val order: SortOrder) : Event()
        data class OnProductClick(val product: Product) : Event()
        object OnRetryClick : Event()
    }

    /** UI STATES */
    data class State(
        val products: List<Product> = emptyList(),
        val isLoading: Boolean = false,
        val error: String? = null,
        val searchQuery: String = "",
        val sortOrder: SortOrder = SortOrder.NAME_ASC
    )

    /*** ONE TIME ACTIONS ***/
    sealed class Effect {
        data class NavigateToDetail(val productId: String) : Effect()
        data class ShowSnackBar(val message: String) : Effect()
    }
}

enum class SortOrder {
    NAME_ASC, PRICE_ASC, PRICE_DESC
}