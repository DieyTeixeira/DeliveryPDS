package com.codek.deliverypds.ui.registers.user.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codek.deliverypds.app.repository.FirestoreRepository
import com.codek.deliverypds.ui.registers.user.state.MessageUserState
import com.codek.deliverypds.ui.registers.user.state.UserUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class UserViewModel(private val firestoreRepository: FirestoreRepository) : ViewModel() {

    private val _userState = MutableStateFlow(UserUiState())
    val userState: StateFlow<UserUiState> get() = _userState

    private val _message = MutableStateFlow<MessageUserState?>(null)
    val message: StateFlow<MessageUserState?> get() = _message

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> get() = _isLoading

    fun updateName(name: String) { _userState.value = _userState.value.copy(name = name) }
    fun updateCpf(cpf: String) { _userState.value = _userState.value.copy(cpf = cpf) }
    fun updatePhone(phone: String) { _userState.value = _userState.value.copy(phone = phone) }

    fun salvarUser() {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                firestoreRepository.salvarUser(_userState.value)
                _message.value = MessageUserState.Success("Usuário salvo com sucesso!")
                resetUserStateAfterDelay()
            } catch (e: Exception) {
                _message.value = MessageUserState.Error("Erro ao salvar usuário: ${e.message}")
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun findUser() {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val user = firestoreRepository.findUser() // Busca o usuário
                if (user != null) {
                    _userState.value = user
                }
            } catch (e: Exception) {
                _message.value = MessageUserState.Error("Erro ao carregar usuário: ${e.message}")
            } finally {
                _isLoading.value = false
            }
        }
    }

    private fun resetUserStateAfterDelay() {
        viewModelScope.launch {
            kotlinx.coroutines.delay(3000)
            _userState.value = UserUiState()
            _message.value = null
        }
    }
}