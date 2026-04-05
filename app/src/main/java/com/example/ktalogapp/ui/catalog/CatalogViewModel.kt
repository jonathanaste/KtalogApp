package com.example.ktalogapp.ui.catalog

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ktalogapp.domain.model.Product
import com.example.ktalogapp.domain.usecase.GetProductsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CatalogViewModel @Inject constructor(
    private val getProductsUseCase: GetProductsUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(CatalogContract.State())
    val state = _state.asStateFlow()

    private val _effect = MutableSharedFlow<CatalogContract.Effect>()
    val effect = _effect.asSharedFlow()

    // Cache para evitar llamadas innecesarias al Repositorio
    private var fullProductList: List<Product> = emptyList()

    init {
        observeSearchChanges()
        handleEvent(CatalogContract.Event.LoadProducts)
    }

    fun handleEvent(event: CatalogContract.Event) {
        when (event) {
            is CatalogContract.Event.LoadProducts -> fetchProducts()
            is CatalogContract.Event.OnSearchQueryChanged -> {
                _state.update { it.copy(searchQuery = event.query) }
            }
            is CatalogContract.Event.OnSortOrderChanged -> {
                _state.update { it.copy(sortOrder = event.order) }
                applyFilters() // El orden es instantáneo al clic
            }
            is CatalogContract.Event.OnProductClick -> {
                sendEffect(CatalogContract.Effect.NavigateToDetail(event.product.id))
            }
            CatalogContract.Event.OnRetryClick -> fetchProducts()
        }
    }

    @OptIn(FlowPreview::class)
    private fun observeSearchChanges() {
        state.map { it.searchQuery }
            .distinctUntilChanged()
            .debounce(300L) // El "Sweet Spot" que hablamos
            .onEach { applyFilters() }
            .flowOn(Dispatchers.Default)
            .launchIn(viewModelScope)
    }

    private fun fetchProducts() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }
            getProductsUseCase().onSuccess { products ->
                fullProductList = products
                applyFilters()
            }.onFailure { error ->
                _state.update { it.copy(isLoading = false, error = error.message) }
            }
        }
    }

    private fun applyFilters() {
        val currentQuery = _state.value.searchQuery
        val currentOrder = _state.value.sortOrder

        // Realizamos el filtrado en un hilo de computación si la lista es grande
        viewModelScope.launch(Dispatchers.Default) {
            val filtered = fullProductList.filter {
                it.name.contains(currentQuery, ignoreCase = true)
            }.let { list ->
                when (currentOrder) {
                    SortOrder.NAME_ASC -> list.sortedBy { it.name }
                    SortOrder.PRICE_ASC -> list.sortedBy { it.price }
                    SortOrder.PRICE_DESC -> list.sortedByDescending { it.price }
                }
            }

            _state.update { it.copy(products = filtered, isLoading = false) }
        }
    }

    private fun sendEffect(effect: CatalogContract.Effect) {
        viewModelScope.launch { _effect.emit(effect) }
    }
}