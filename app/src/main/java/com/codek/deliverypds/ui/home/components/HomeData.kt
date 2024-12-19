package com.codek.deliverypds.ui.home.components

import com.codek.deliverypds.R

data class Product(
    val id: Int,
    val name: String,
    val price: Double,
    val imageRes: Int,
    val categoryId: Int
)

val categories = listOf(
    "Todos",
    "Bolos",
    "Pizzas",
    "Lasanhas",
    "Panquecas"
)

val products = listOf(
    Product(1, "Bolo de Pote Limão", 10.00, R.drawable.bolo_limao, 1),
    Product(2, "Bolo de Pote Brigadeiro", 10.00, R.drawable.bolo_brigadeiro, 1),
    Product(3, "Bolo de Pote Maracujá", 10.00, R.drawable.bolo_maracuja, 1),
    Product(4, "Mini Pizza Calabresa", 5.00, R.drawable.pizza_calabresa, 2),
    Product(5, "Pizza Broto 4 Queijos", 25.00, R.drawable.pizza_queijo, 2),
    Product(6, "Lasanha Bolonhesa", 30.00, R.drawable.lasanha_bolonhesa, 3),
    Product(7, "Panqueca de Frango", 15.00, R.drawable.panqueca_frango, 4)
)