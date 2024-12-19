package com.codek.deliverypds.ui.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import com.codek.deliverypds.ui.home.state.Category
import com.codek.deliverypds.ui.home.state.Product
import com.codek.deliverypds.ui.home.state.categories
import com.codek.deliverypds.ui.home.state.products

class HomeViewModel : ViewModel() {
    var searchText by mutableStateOf("")
    var selectedCategory by mutableStateOf(0)
    var selectedProduct by mutableStateOf<Product?>(null)

    val filteredProducts: List<Product>
        get() = products.filter {
            (selectedCategory == 0 || it.categoryId == selectedCategory) &&
                    it.name.contains(searchText, ignoreCase = true)
        }

    val category: List<Category> = categories
}