package com.codek.deliverypds.app.states

import androidx.compose.runtime.mutableStateOf
import java.text.NumberFormat
import java.util.Locale
import kotlin.collections.filter
import kotlin.collections.find
import kotlin.collections.map
import kotlin.collections.plus

data class CartItem(
    val id: Int,
    val name: String,
    val quantity: Int,
    val imageRes: Int,
    val price: Double,
    val totalPrice: Double = price * quantity
)

class CartState {
    private val _items = mutableStateOf<List<CartItem>>(emptyList())
    val items: List<CartItem> get() = _items.value

    // Adiciona ou atualiza um item no carrinho
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

    // Remove um item do carrinho
    fun removeItem(itemId: Int) {
        _items.value = _items.value.filter { it.id != itemId }
    }

    // Limpa todos os itens do carrinho
    fun clearCart() {
        _items.value = emptyList()
    }
}

fun formatPrice(price: Double): String {
    val formatter = NumberFormat.getCurrencyInstance(Locale("pt", "BR"))
    return formatter.format(price)
}