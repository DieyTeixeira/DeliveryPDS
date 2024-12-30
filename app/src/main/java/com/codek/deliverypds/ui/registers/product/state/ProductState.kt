package com.codek.deliverypds.ui.registers.product.state

sealed class MessageProductState(val message: String) {
    class Success(message: String) : MessageProductState(message)
    class Error(message: String) : MessageProductState(message)
}

data class ProductUiState(
    val name: String = "",
    val category: String = "",
    val categoryID: String = "",
    val value: String = "",
    val link: String = ""
)