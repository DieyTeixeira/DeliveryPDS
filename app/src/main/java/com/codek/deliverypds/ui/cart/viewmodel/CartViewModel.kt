package com.codek.deliverypds.ui.cart.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.codek.deliverypds.ui.cart.state.CartItem
import java.text.NumberFormat
import java.util.Locale

class CartViewModel : ViewModel() {
    private val _items = mutableStateOf<List<CartItem>>(emptyList())
    val items: List<CartItem> get() = _items.value

    // Adicionar ou atualizar item no carrinho
    fun addItem(productId: Int, name: String, price: Double, imageRes: Int, quantity: Int) {
        val existingItem = _items.value.find { it.id == productId }
        if (existingItem != null) {
            _items.value = _items.value.map {
                if (it.id == productId) it.copy(quantity = it.quantity + quantity) else it
            }
        } else {
            _items.value = _items.value + CartItem(productId, name, quantity, imageRes, price)
        }
    }

    // Remover um item do carrinho
    fun removeItem(itemId: Int) {
        _items.value = _items.value.filter { it.id != itemId }
    }

    // Limpar todos os itens do carrinho
    fun clearCart() {
        _items.value = emptyList()
    }

    // Formatar preço
    fun formatPrice(price: Double): String {
        val formatter = NumberFormat.getCurrencyInstance(Locale("pt", "BR"))
        return formatter.format(price)
    }

    // Calcular o total de itens no carrinho
    fun cartItemCount(): Int {
        return _items.value.sumOf { it.quantity }
    }

    // Calcular o preço total de todos os itens no carrinho (quantidade * preço por item)
    fun totalCartPrice(): Double {
        return _items.value.sumOf { it.price * it.quantity }
    }
}