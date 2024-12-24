package com.codek.deliverypds.app.configs

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast

@SuppressLint("QueryPermissionsNeeded")
fun SendMessageToWhatsApp(context: Context, phoneNumber: String, message: String) {

    val encodedMessage = Uri.encode(message)
    val url = "https://wa.me/$phoneNumber?text=$encodedMessage"

    val intent = Intent(Intent.ACTION_VIEW)
    intent.data = Uri.parse(url)

    if (intent.resolveActivity(context.packageManager) != null) {
        context.startActivity(intent)
    } else {
        Toast.makeText(context, "WhatsApp não instalado", Toast.LENGTH_SHORT).show()
    }
}