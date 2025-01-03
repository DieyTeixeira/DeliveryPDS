package com.codek.deliverypds.ui.registers.product.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codek.deliverypds.app.repository.FirestoreRepository
import com.codek.deliverypds.ui.registers.category.state.CategoryUiState
import com.codek.deliverypds.ui.registers.product.state.MessageProductState
import com.codek.deliverypds.ui.registers.product.state.ProductUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProductViewModel(private val firestoreRepository: FirestoreRepository) : ViewModel() {

    private val _productState = MutableStateFlow(ProductUiState())
    val productState: StateFlow<ProductUiState> get() = _productState

    private val _products = MutableLiveData<List<ProductUiState>>()
    val products: LiveData<List<ProductUiState>> get() = _products

    private val _categories = MutableStateFlow<List<CategoryUiState>>(emptyList())
    val categories: StateFlow<List<CategoryUiState>> get() = _categories

    private val _message = MutableStateFlow<MessageProductState?>(null)
    val message: StateFlow<MessageProductState?> get() = _message

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> get() = _isLoading

    fun updateProductName(product_name: String) { _productState.value = _productState.value.copy(name = product_name) }
    fun updateProductCategory(product_category: String) { _productState.value = _productState.value.copy(category = product_category) }
    fun updateProductValue(product_value: String) { _productState.value = _productState.value.copy(value = product_value) }
    fun updateProductLink(product_link: String) { _productState.value = _productState.value.copy(link = product_link) }
    fun updateSelectedCategory(category: String, categoryID: String) { _productState.value = _productState.value.copy(category = category, categoryID = categoryID) }

    fun loadProducts() {
        viewModelScope.launch {
            val productList = firestoreRepository.listarProducts()
            _products.postValue(productList)
        }
    }

    fun loadCategories() {
        viewModelScope.launch {
            val categoryList = firestoreRepository.listarCategories()
            _categories.value = categoryList
        }
    }

    fun salvarProduct() {
        if (_productState.value.name.isBlank() || _productState.value.value.isBlank() || _productState.value.category.isBlank()) {
            _message.value = MessageProductState.Error("Por favor, não deixe campos vazios!")
            return
        }

        _isLoading.value = true
        viewModelScope.launch {
            try {
                firestoreRepository.salvarProduct(_productState.value)
                _message.value = MessageProductState.Success("Produto salvo com sucesso!")
                resetProductStateAfterDelay()
            } catch (e: Exception) {
                _message.value = MessageProductState.Error("Erro ao salvar produto: ${e.message}")
            } finally {
                _isLoading.value = false
            }
        }
    }

    private fun resetProductStateAfterDelay() {
        viewModelScope.launch {
            kotlinx.coroutines.delay(3000)
            _productState.value = ProductUiState()
            _message.value = null
        }
    }
}