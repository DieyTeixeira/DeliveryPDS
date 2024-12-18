package com.codek.deliverypds.ui.login.viewmodel

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codek.deliverypds.app.repository.AuthRepository
import com.codek.deliverypds.ui.login.state.LoginState
import com.codek.deliverypds.ui.login.state.LoginUiState
import com.google.firebase.auth.FirebaseAuthException
import kotlinx.coroutines.delay
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

//        viewModelScope.launch {
//            _loginState.value = authRepository.signIn(email, password)
//        }

        viewModelScope.launch {
            delay(2000)
            try {
                _loginState.value = authRepository.signIn(email, password)
            } catch (e: Exception) {
                val errorMessage = when (e.message) {
                    "ERROR_INVALID_CREDENTIAL" -> "Erro de credencial"
                    "ERROR_INVALID_EMAIL" -> "O email fornecido é inválido."
                    "ERROR_USER_NOT_FOUND" -> "Usuário não encontrado. Verifique o email e tente novamente."
                    "ERROR_WRONG_PASSWORD" -> "Senha incorreta. Tente novamente."
                    "ERROR_USER_DISABLED" -> "Esta conta foi desativada. Entre em contato com o suporte."
                    else -> "Erro ao tentar fazer login: ${e.message}. Tente novamente mais tarde."
                }
                _loginState.value = LoginState.Error(errorMessage)
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