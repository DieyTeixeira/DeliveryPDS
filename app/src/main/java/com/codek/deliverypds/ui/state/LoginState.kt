package com.codek.deliverypds.ui.state

sealed class LoginState {
    object Idle : LoginState()
    object Loading : LoginState()
    data class Success(val userId: String) : LoginState()
    data class Error(val message: String) : LoginState()
}

data class LoginUiState(
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = ""
)