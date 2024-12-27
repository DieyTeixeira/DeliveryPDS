package com.codek.deliverypds.ui.registers.address.state

sealed class MessageAddressState(val message: String) {
    class Success(message: String) : MessageAddressState(message)
    class Error(message: String) : MessageAddressState(message)
}

data class AddressUiState(
    val street: String = "",
    val number: String = "",
    val complement: String = "",
    val cep: String = "",
    val district: String = "",
    val city: String = ""
)