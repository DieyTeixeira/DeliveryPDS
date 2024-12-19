package com.codek.deliverypds.ui.cart.state

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
    val price: Double
)