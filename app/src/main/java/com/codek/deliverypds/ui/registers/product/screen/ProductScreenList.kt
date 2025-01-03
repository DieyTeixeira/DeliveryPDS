package com.codek.deliverypds.ui.registers.product.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.codek.deliverypds.app.configs.BarNavigation
import com.codek.deliverypds.app.theme.ColorError
import com.codek.deliverypds.app.theme.ColorSec
import com.codek.deliverypds.app.theme.DarkColorError
import com.codek.deliverypds.ui.registers.components.RegistersHeader
import com.codek.deliverypds.ui.registers.product.state.ProductUiState
import com.codek.deliverypds.ui.registers.product.viewmodel.ProductViewModel

@Composable
fun ProductScreenList(
    productViewModel: ProductViewModel,
    onNavigateToHome: () -> Unit,
    onNavigateToConfig: () -> Unit,
    onSignOutClick: () -> Unit
) {

    LaunchedEffect(Unit) {
        productViewModel.loadProducts()
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
            RegistersHeader("Produtos Cadastrados")
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(15.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (!productViewModel.products.value.isNullOrEmpty()) {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(productViewModel.products.value!!) { product ->
                            ProductsItemRow(
                                product = product
                            )
                        }
                    }
                } else {
                    Spacer(modifier = Modifier.height(20.dp))
                    Text(
                        text = "Nenhum produto cadastrado",
                        fontSize = 16.sp,
                        color = Color.Gray
                    )
                }
            }
        }
    }
}

@Composable
fun ProductsItemRow(
    product: ProductUiState
) {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                Color.White,
                RoundedCornerShape(16.dp)
            )
            .clip(RoundedCornerShape(16.dp)),
        contentAlignment = Alignment.CenterStart
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = rememberAsyncImagePainter(product.link),
                contentDescription = product.name,
                modifier = Modifier
                    .size(95.dp)
                    .border(1.dp, Color.LightGray, RoundedCornerShape(12.dp))
                    .aspectRatio(1f)
                    .clip(RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(10.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = product.name,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
                Text(
                    text = product.category,
                    fontSize = 14.sp,
                    color = Color.DarkGray
                )
                Text(
                    text = product.value,
                    fontSize = 16.sp,
                    color = DarkColorError
                )
            }
        }
    }
}

@Preview
@Composable
private fun PreviewProductScreenList() {
    val productUiState = ProductUiState(
        name = "Pote com tampa hermética de vidro temperado",
        category = "Cozinha",
        value = "R$ 19,99",
        link = "https://firebasestorage.googleapis.com/v0/b/deliverypds-2024.firebasestorage.app/o/pote.jpg?alt=media&token=8e098948-5742-4be8-8eab-99fc084b56da" // Link de imagem simulada
    )
    ProductsItemRow(
        product = productUiState
    )
}