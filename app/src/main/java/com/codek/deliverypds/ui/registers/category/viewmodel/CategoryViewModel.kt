package com.codek.deliverypds.ui.registers.category.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codek.deliverypds.app.repository.FirestoreRepository
import com.codek.deliverypds.ui.registers.category.state.CategoryUiState
import com.codek.deliverypds.ui.registers.category.state.MessageCategoryState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CategoryViewModel(private val firestoreRepository: FirestoreRepository) : ViewModel() {

    private val _categoryState = MutableStateFlow(CategoryUiState())
    val categoryState: StateFlow<CategoryUiState> get() = _categoryState

    private val _categories = MutableLiveData<List<CategoryUiState>>()
    val categories: LiveData<List<CategoryUiState>> get() = _categories

    private val _message = MutableStateFlow<MessageCategoryState?>(null)
    val message: StateFlow<MessageCategoryState?> get() = _message

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> get() = _isLoading

    fun updateCategoryName(category_name: String) { _categoryState.value = _categoryState.value.copy(category = category_name) }
    fun updateCategoryLink(product_link: String) { _categoryState.value = _categoryState.value.copy(link = product_link) }

    fun loadCategories() {
        viewModelScope.launch {
            val categoryList = firestoreRepository.listarCategories()
            _categories.postValue(categoryList)
        }
    }

    fun salvarCategory() {
        if (_categoryState.value.category.isBlank()) {
            _message.value = MessageCategoryState.Error("Por favor, não deixe campos vazios!")
            return
        }

        _isLoading.value = true
        viewModelScope.launch {
            try {
                firestoreRepository.salvarCategory(_categoryState.value)
                _message.value = MessageCategoryState.Success("Categoria salva com sucesso!")
                resetCategoryStateAfterDelay()
            } catch (e: Exception) {
                _message.value = MessageCategoryState.Error("Erro ao salvar categoria: ${e.message}")
            } finally {
                _isLoading.value = false
            }
        }
    }

    private fun resetCategoryStateAfterDelay() {
        viewModelScope.launch {
            delay(3000)
            _categoryState.value = CategoryUiState()
            _message.value = null
        }
    }
}