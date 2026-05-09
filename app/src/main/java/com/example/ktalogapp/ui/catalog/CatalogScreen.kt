package com.example.ktalogapp.ui.catalog

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.ktalogapp.ui.components.CatalogSearchBar
import com.example.ktalogapp.ui.components.ErrorMessage
import com.example.ktalogapp.ui.components.ProductItem

@Composable
fun CatalogScreen(
    viewModel: CatalogViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    var showSortMenu by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            CatalogSearchBar(
                query = state.searchQuery,
                onQueryChange = { viewModel.handleEvent(CatalogContract.Event.OnSearchQueryChanged(it)) },
                onFilterClick = { showSortMenu = true },
                showSortMenu = showSortMenu,
                onDismissMenu = { showSortMenu = false },
                onSortOptionClick = { order ->
                    viewModel.handleEvent(CatalogContract.Event.OnSortOrderChanged(order))
                    showSortMenu = false
                }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            when {
                state.isLoading -> {
                    CircularProgressIndicator()
                }

                state.error != null -> {
                    ErrorMessage(
                        message = state.error.orEmpty(),
                        onRetry = { viewModel.handleEvent(CatalogContract.Event.OnRetryClick) }
                    )
                }

                state.products.isEmpty() -> {
                    Text(text = "No se encontraron productos en La Botica")
                }

                else -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(8.dp)
                    ) {
                        items(
                            items = state.products,
                            key = { it.id }
                        ) { product ->
                            ProductItem(
                                product = product,
                                onClick = { viewModel.handleEvent(CatalogContract.Event.OnProductClick(product)) }
                            )
                        }
                    }
                }
            }
        }
    }
}
