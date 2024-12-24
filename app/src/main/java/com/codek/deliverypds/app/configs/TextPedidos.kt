package com.codek.deliverypds.app.configs

import com.codek.deliverypds.ui.cart.viewmodel.CartViewModel

fun generateCartMessage(
    cartViewModel: CartViewModel,
    tipoPagamento: String
): String {
    val builder = StringBuilder()

    builder.append("\uD83D\uDCC4 *PEDIDO Nº* ${System.currentTimeMillis()}\n\n")
    builder.append("\uD83D\uDED2 *ITENS*\n")
    cartViewModel.items.forEachIndexed { index, item ->
        builder.append("➡ ${item.quantity}x ${item.name}\n")
        builder.append("        Valor ${cartViewModel.formatPrice(item.price)}\n\n") // 8 espaços
    }

    builder.append("💳 *MÉTODO DE PAGAMENTO*\n")
    builder.append("        ${tipoPagamento}\n\n")

    builder.append("🛵 *TIPO DE ENTREGA*\n")
    builder.append("Não informado\n")
    builder.append("        Taxa R$ 7,00\n\n")

    builder.append("🏠 *ENDEREÇO*\n")
    builder.append("Não informado\n\n")

    builder.append("\uD83D\uDCB0 *TOTAL DO PEDIDO* ${cartViewModel.formatPrice(cartViewModel.totalCartPrice())}\n\n")

    builder.append("Obrigado pela preferência, se precisar de algo é só chamar! 😉")

    return builder.toString()
}