package com.codek.deliverypds.ui.registers.user.state

sealed class MessageUserState(val message: String) {
    class Success(message: String) : MessageUserState(message)
    class Error(message: String) : MessageUserState(message)
}

data class UserUiState(
    val name: String = "",
    val cpf: String = "",
    val phone: String = ""
)