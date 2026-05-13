package com.example.ktalogapp.ui.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ktalogapp.domain.usecase.GetProductByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val getProductByIdUseCase: GetProductByIdUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = MutableStateFlow(DetailContract.State())
    val state = _state.asStateFlow()

    private val _effect = MutableSharedFlow<DetailContract.Effect>()
    val effect = _effect.asSharedFlow()

    init {
        val productId: String? = savedStateHandle["productId"]
        productId?.let {
            handleEvent(DetailContract.Event.LoadProduct(it))
        } ?: run {
            _state.update { it.copy(isLoading = false, error = "Product ID not found") }
        }
    }

    fun handleEvent(event: DetailContract.Event) {
        when (event) {
            is DetailContract.Event.LoadProduct -> fetchProduct(event.id)
            DetailContract.Event.OnBackClick -> sendEffect(DetailContract.Effect.NavigateBack)
            DetailContract.Event.OnAddToCartClick -> {
                sendEffect(DetailContract.Effect.ShowSnackBar("Producto añadido al carrito"))
            }
            DetailContract.Event.OnRetryClick -> {
                _state.value.product?.id?.let { fetchProduct(it) }
            }
        }
    }

    private fun fetchProduct(id: String) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }
            getProductByIdUseCase(id).onSuccess { product ->
                _state.update { it.copy(isLoading = false, product = product) }
            }.onFailure { error ->
                _state.update { it.copy(isLoading = false, error = error.message) }
            }
        }
    }

    private fun sendEffect(effect: DetailContract.Effect) {
        viewModelScope.launch { _effect.emit(effect) }
    }
}
