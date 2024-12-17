package com.codek.deliverypds.ui.viewmodel

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codek.deliverypds.repository.AuthRepository
import com.codek.deliverypds.ui.state.LoginState
import com.codek.deliverypds.ui.state.LoginUiState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel(private val authRepository: AuthRepository) : ViewModel() {

    private val _loginState = MutableStateFlow<LoginState>(LoginState.Idle)
    val loginState: StateFlow<LoginState> get() = _loginState

    private val _loginUiState = MutableStateFlow(LoginUiState())
    val loginUiState = _loginUiState.asStateFlow()

    fun onEmailChange(newEmail: String) {
        _loginUiState.value = _loginUiState.value.copy(email = newEmail)
    }

    fun onPasswordChange(newPassword: String) {
        _loginUiState.value = _loginUiState.value.copy(password = newPassword)
    }

    fun onConfirmPasswordChange(newPassword: String) {
        _loginUiState.value = _loginUiState.value.copy(confirmPassword = newPassword)
    }

    private fun isEmailValid(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun isPasswordValid(password: String): Boolean {
        return password.length >= 6
    }

    // Login
    fun signIn() {
        val email = _loginUiState.value.email
        val password = _loginUiState.value.password

        _loginState.value = LoginState.Loading
        viewModelScope.launch {
            _loginState.value = authRepository.signIn(email, password)
        }
    }

    // Sign Up (Criar conta)
    fun signUp() {
        val email = _loginUiState.value.email
        val password = _loginUiState.value.password

        _loginState.value = LoginState.Loading
        viewModelScope.launch {
            _loginState.value = authRepository.signUp(email, password)
        }
    }

    // Logout
    fun signOut() {
        authRepository.signOut()
        _loginState.value = LoginState.Idle // Reseta o estado
    }
}