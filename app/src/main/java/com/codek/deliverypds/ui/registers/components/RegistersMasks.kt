package com.codek.deliverypds.ui.registers.components

fun formatCepMask(cep: String): String {
    val cleaned = cep.replace(Regex("[^\\d]"), "")
    if (cleaned.isEmpty()) return ""
    return when (cleaned.length) {
        in 1..5 -> cleaned // Apenas os primeiros dígitos
        else -> "${cleaned.substring(0, 5)}-${cleaned.substring(5)}" // Adiciona o hífen
    }
}

fun formatCpfMask(cpf: String): String {
    val cleaned = cpf.replace(Regex("[^\\d]"), "")
    if (cleaned.isEmpty()) return ""
    return when (cleaned.length) {
        in 1..3 -> cleaned // Apenas os primeiros dígitos
        in 4..6 -> "${cleaned.substring(0, 3)}.${cleaned.substring(3)}" // Adiciona o primeiro ponto
        in 7..9 -> "${cleaned.substring(0, 3)}.${cleaned.substring(3, 6)}.${cleaned.substring(6)}" // Adiciona o segundo ponto
        else -> "${cleaned.substring(0, 3)}.${cleaned.substring(3, 6)}.${cleaned.substring(6, 9)}-${cleaned.substring(9)}" // Adiciona o hífen
    }
}

fun formatPhoneMask(phone: String): String {
    val cleaned = phone.replace(Regex("[^\\d]"), "")
    if (cleaned.isEmpty()) return ""
    return when (cleaned.length) {
        in 1..2 -> "($cleaned"
        in 3..6 -> "(${cleaned.substring(0, 2)}) ${cleaned.substring(2)}"
        in 7..10 -> "(${cleaned.substring(0, 2)}) ${cleaned.substring(2, 6)}-${cleaned.substring(6)}"
        else -> "(${cleaned.substring(0, 2)}) ${cleaned.substring(2, 7)}-${cleaned.substring(7)}"
    }
}