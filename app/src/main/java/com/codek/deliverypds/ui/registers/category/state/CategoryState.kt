package com.codek.deliverypds.ui.registers.category.state

sealed class MessageCategoryState(val message: String) {
    class Success(message: String) : MessageCategoryState(message)
    class Error(message: String) : MessageCategoryState(message)
}

data class CategoryUiState(
    val itemID: String = "",
    val category: String = "",
    val link: String = ""
)