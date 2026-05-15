package com.example.ktalogapp.ui.detail

import com.example.ktalogapp.domain.model.Product

sealed class DetailContract {
    sealed class Event {
        data class LoadProduct(val id: String) : Event()
        object OnBackClick : Event()
        object OnAddToCartClick : Event()
        object OnRetryClick : Event()
    }

    data class State(
        val product: Product? = null,
        val isLoading: Boolean = true,
        val error: String? = null
    )

    sealed class Effect {
        object NavigateBack : Effect()
        data class ShowSnackBar(val message: String) : Effect()
    }
}
