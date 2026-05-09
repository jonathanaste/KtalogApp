package com.example.ktalogapp.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Sort
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.ktalogapp.ui.catalog.SortOrder

@Composable
fun CatalogSearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    onFilterClick: () -> Unit,
    showSortMenu: Boolean,
    onDismissMenu: () -> Unit,
    onSortOptionClick: (SortOrder) -> Unit
) {
    OutlinedTextField(
        value = query,
        onValueChange = onQueryChange,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        placeholder = { Text("Buscar en La Botica...") },
        leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
        trailingIcon = {
            Box {
                IconButton(onClick = onFilterClick) {
                    Icon(Icons.AutoMirrored.Filled.Sort, contentDescription = "Filtrar")
                }
                DropdownMenu(
                    expanded = showSortMenu,
                    onDismissRequest = onDismissMenu
                ) {
                    DropdownMenuItem(
                        text = { Text("Nombre (A-Z)") },
                        onClick = { onSortOptionClick(SortOrder.NAME_ASC) }
                    )
                    DropdownMenuItem(
                        text = { Text("Precio (Menor a Mayor)") },
                        onClick = { onSortOptionClick(SortOrder.PRICE_ASC) }
                    )
                    DropdownMenuItem(
                        text = { Text("Precio (Mayor a Menor)") },
                        onClick = { onSortOptionClick(SortOrder.PRICE_DESC) }
                    )
                }
            }
        },
        shape = RoundedCornerShape(24.dp),
        singleLine = true
    )
}
