package com.codek.deliverypds.ui.registers.product.screen

import android.graphics.Bitmap
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
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.codek.deliverypds.app.configs.BarNavigation
import com.codek.deliverypds.app.theme.ColorError
import com.codek.deliverypds.app.theme.ColorSec
import com.codek.deliverypds.app.theme.ColorSucess
import com.codek.deliverypds.app.theme.DarkColorError
import com.codek.deliverypds.app.theme.DarkColorSucess
import com.codek.deliverypds.ui.config.viewmodel.RegistersViewModel
import com.codek.deliverypds.ui.registers.components.RegistersHeader
import com.codek.deliverypds.ui.registers.components.RegistersPhotoPicker
import com.codek.deliverypds.ui.registers.components.FieldTextString
import com.codek.deliverypds.ui.registers.components.RegistersLoading
import com.codek.deliverypds.ui.registers.components.RegistersMessage
import com.codek.deliverypds.ui.registers.product.state.MessageProductState
import com.codek.deliverypds.ui.registers.product.viewmodel.ProductViewModel

@Composable
fun ProductScreenEdit(
    registersViewModel: RegistersViewModel,
    productViewModel: ProductViewModel,
    onNavigateToHome: () -> Unit,
    onNavigateToConfig: () -> Unit,
    onSignOutClick: () -> Unit
) {

    val context = LocalContext.current
    var bitmapImage: Bitmap? by remember { mutableStateOf(null) }
    var expanded by remember { mutableStateOf(false) }

    val productState = productViewModel.productState.collectAsState()
    val categories = productViewModel.categories.collectAsState()
    val message = productViewModel.message.collectAsState()

    LaunchedEffect(Unit) {
        productViewModel.loadCategories()
    }

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
                        FieldTextString(
                            text = productState.value.name,
                            onTextChange = { productViewModel.updateProductName(it) },
                            placeHolder = "Nome do Produto"
                        )
//                        Box(modifier = Modifier.fillMaxWidth()) {
//                            TextField(
//                                value = productState.value.category,
//                                onValueChange = {},
//                                modifier = Modifier.clickable { expanded = true },
//                                readOnly = true,
//                                placeholder = { Text("Selecione uma Categoria") },
//                                colors = TextFieldDefaults.textFieldColors(
//                                    backgroundColor = Color.White
//                                )
//                            )
//                            DropdownMenu(
//                                expanded = expanded,
//                                onDismissRequest = { expanded = false }
//                            ) {
//                                categories.value.forEach { category ->
//                                    DropdownMenuItem(onClick = {
//                                        productViewModel.updateSelectedCategory(
//                                            category.category,
//                                            category.itemID
//                                        )
//                                        expanded = false
//                                    }) {
//                                        Text(category.category)
//                                    }
//                                }
//                            }
//                        }
                        FieldTextString(
                            text = productState.value.category,
                            onTextChange = { productViewModel.updateProductCategory(it) },
                            placeHolder = "Categoria do Produto"
                        )
                        FieldTextString(
                            text = productState.value.value,
                            onTextChange = { productViewModel.updateProductValue(it) },
                            placeHolder = "Valor do Produto"
                        )
                    }
                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp),
                    contentAlignment = Alignment.Center
                ) {
                    when {
                        productViewModel.isLoading.collectAsState().value -> RegistersLoading(Color.DarkGray)
                        message.value is MessageProductState.Success -> RegistersMessage(
                            message = (message.value as MessageProductState.Success).message,
                            color = Pair(ColorSucess, DarkColorSucess),
                            onNavigateToConfig = onNavigateToConfig
                        )
                        message.value is MessageProductState.Error -> RegistersMessage(
                            message = (message.value as MessageProductState.Error).message,
                            color = Pair(ColorError, DarkColorError),
                            onNavigateToConfig = { }
                        )
                    }
                }
                Spacer(modifier = Modifier.height(15.dp))
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
                            if (bitmapImage != null) {
                                registersViewModel.uploadPhoto(
                                    context = context,
                                    bitmap = bitmapImage!!,
                                    fileName = productState.value.name
                                ) { link ->
                                    if (link != null) {
                                        productViewModel.updateProductLink(link)
                                        productViewModel.salvarProduct()
                                    }
                                }
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