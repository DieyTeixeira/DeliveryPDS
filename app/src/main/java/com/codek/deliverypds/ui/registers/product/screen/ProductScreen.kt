package com.codek.deliverypds.ui.registers.product.screen

import android.graphics.Bitmap
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import com.codek.deliverypds.app.configs.BarNavigation
import com.codek.deliverypds.app.theme.ColorSec
import com.codek.deliverypds.ui.config.viewmodel.RegistersViewModel
import com.codek.deliverypds.ui.registers.components.RegistersHeader
import com.codek.deliverypds.ui.registers.components.RegistersPhotoPicker
import com.codek.deliverypds.ui.registers.components.UserFieldText

@Composable
fun ProductScreen(
    registersViewModel: RegistersViewModel,
    onNavigateToHome: () -> Unit,
    onNavigateToConfig: () -> Unit,
    onSignOutClick: () -> Unit
) {

    val context = LocalContext.current
    val clipboardManager = LocalClipboardManager.current
    var linkPhotoProduct by remember { mutableStateOf("") }
    var productName by remember { mutableStateOf("") }
    var categoryName by remember { mutableStateOf("") }
    var productValue by remember { mutableStateOf("") }
    var bitmapImage: Bitmap? by remember { mutableStateOf(null) }

    BarNavigation(
        onNavigateToHome = { onNavigateToHome() },
        onNavigateToConfig = { onNavigateToConfig() },
        onSignOut = { onSignOutClick() }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.LightGray.copy(alpha = 0.5f)),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            RegistersHeader("Cadastrar Produtos")
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(15.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(top = 15.dp),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    RegistersPhotoPicker { selectedBitmap ->
                        bitmapImage = selectedBitmap
                    }
                    Spacer(modifier = Modifier.height(20.dp))
                    Column(
                        modifier = Modifier
                            .fillMaxWidth(0.85f),
                        verticalArrangement = Arrangement.spacedBy(15.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        UserFieldText(
                            text = productName,
                            onTextChange = { productName = it },
                            placeHolder = "Nome do Produto"
                        )
                        UserFieldText(
                            text = categoryName,
                            onTextChange = { categoryName = it },
                            placeHolder = "Categoria do Produto"
                        )
                        UserFieldText(
                            text = productValue,
                            onTextChange = { productValue = it },
                            placeHolder = "Valor do Produto"
                        )
                    }
                }
                Box(
                    modifier = Modifier
                        .width(175.dp)
                        .height(40.dp)
                        .background(
                            ColorSec,
                            RoundedCornerShape(16.dp)
                        )
                        .clickable(
                            indication = null,
                            interactionSource = remember { MutableInteractionSource() }
                        ) {
                            if (bitmapImage != null && productName.isNotBlank() && productValue.isNotBlank()) {
                                registersViewModel.uploadPhotoProduct(
                                    context = context,
                                    bitmap = bitmapImage!!,
                                    fileName = productName
                                ) { link ->
                                    if (link != null) {
                                        linkPhotoProduct = link
                                        clipboardManager.setText(AnnotatedString(linkPhotoProduct))
                                        Toast.makeText(
                                            context,
                                            "Produto cadastrado com sucesso!",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                        onNavigateToHome()
                                    } else {
                                        Toast.makeText(
                                            context,
                                            "Erro ao cadastrar o produto.",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                }
                            } else {
                                val message = when {
                                    productName.isBlank() -> "Por favor, insira o nome do produto!"
                                    productValue.isBlank() -> "Por favor, insira o valor do produto!"
                                    else -> "Selecione uma imagem do produto!"
                                }
                                Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                            }
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Cadastrar Produto",
                        color = Color.White
                    )
                }
                Spacer(modifier = Modifier.height(55.dp))
            }
        }
    }
}