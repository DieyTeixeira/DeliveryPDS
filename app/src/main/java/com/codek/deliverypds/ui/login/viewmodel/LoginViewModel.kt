package com.codek.deliverypds.ui.login.viewmodel

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codek.deliverypds.app.repository.AuthRepository
import com.codek.deliverypds.ui.login.state.LoginState
import com.codek.deliverypds.ui.login.state.LoginUiState
import com.google.firebase.auth.FirebaseAuthException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
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

    // Login
    fun signIn() {
        val email = _loginUiState.value.email
        val password = _loginUiState.value.password

        if (!validateField(email.isEmpty(), "Por favor, insira seu email")) return
        if (!validateField(!Patterns.EMAIL_ADDRESS.matcher(email).matches(), "Formato de email inválido")) return
        if (!validateField(password.isEmpty(), "Por favor, insira sua senha")) return
        if (!validateField(password.length < 8, "A senha deve ter no mínimo 8 caracteres")) return

        _loginState.value = LoginState.Loading

        viewModelScope.launch {
            try {
                _loginState.value = authRepository.signIn(email, password)
            } catch (e: FirebaseAuthException) {
                _loginState.value = LoginState.Error(e.errorCode)
            } catch (e: Exception) {
                _loginState.value = LoginState.Error("Erro desconhecido. Tente novamente mais tarde.")
            }
        }
    }

    // Sign Up (Criar conta)
    fun signUp() {
        val email = _loginUiState.value.email
        val password = _loginUiState.value.password

        if (!validateField(email.isEmpty(), "Por favor, insira seu email")) return
        if (!validateField(!Patterns.EMAIL_ADDRESS.matcher(email).matches(), "Formato de email inválido")) return

        _loginState.value = LoginState.Loading
        viewModelScope.launch {
            _loginState.value = authRepository.signUp(email, password)
        }
    }

    // Logout
    fun signOut() {
        authRepository.signOut()
        _loginState.value = LoginState.Idle
        _loginState.value = LoginState.LoggedOut
    }

    private fun validateField(condition: Boolean, message: String): Boolean {
        return if (condition) {
            _loginState.value = LoginState.Error(message)
            false
        } else {
            true
        }
    }
}