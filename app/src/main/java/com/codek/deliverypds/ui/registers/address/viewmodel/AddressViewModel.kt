package com.codek.deliverypds.ui.registers.address.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codek.deliverypds.app.repository.FirestoreRepository
import com.codek.deliverypds.ui.registers.address.state.AddressUiState
import com.codek.deliverypds.ui.registers.address.state.MessageAddressState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AddressViewModel(private val firestoreRepository: FirestoreRepository) : ViewModel() {

    private val _addressState = MutableStateFlow(AddressUiState())
    val addressState: StateFlow<AddressUiState> get() = _addressState

    private val _message = MutableStateFlow<MessageAddressState?>(null)
    val message: StateFlow<MessageAddressState?> get() = _message

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> get() = _isLoading

    fun updateStreet(street: String) { _addressState.value = _addressState.value.copy(street = street) }
    fun updateNumber(number: String) { _addressState.value = _addressState.value.copy(number = number) }
    fun updateComplement(complement: String) { _addressState.value = _addressState.value.copy(complement = complement) }
    fun updateCep(cep: String) { _addressState.value = _addressState.value.copy(cep = cep) }
    fun updateDistrict(district: String) { _addressState.value = _addressState.value.copy(district = district) }
    fun updateCity(city: String) { _addressState.value = _addressState.value.copy(city = city) }

    fun salvarAddress() {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                firestoreRepository.salvarAddress(_addressState.value)
                _message.value = MessageAddressState.Success("Endereço salvo com sucesso!")
                resetAddressStateAfterDelay()
            } catch (e: Exception) {
                _message.value = MessageAddressState.Error("Erro ao salvar endereço: ${e.message}")
            } finally {
                _isLoading.value = false
            }
        }
    }

    private fun resetAddressStateAfterDelay() {
        viewModelScope.launch {
            kotlinx.coroutines.delay(3000)
            _addressState.value = AddressUiState()
            _message.value = null
        }
    }
}