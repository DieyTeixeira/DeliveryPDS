package com.codek.deliverypds.ui.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.codek.deliverypds.app.theme.ColorSec
import com.codek.deliverypds.ui.cart.viewmodel.CartViewModel
import com.codek.deliverypds.ui.home.state.Product
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeProducts(
    filteredProducts: List<Product>,
    onProductSelected: (Product) -> Unit = {}
) {
    LazyVerticalGrid(
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp, 0.dp),
        columns = GridCells.Fixed(2),
        verticalArrangement = Arrangement.spacedBy(0.dp),
        horizontalArrangement = Arrangement.spacedBy(15.dp)
    ) {
        items(filteredProducts.size) { index ->
            val product = filteredProducts[index]
            ProductCard(
                imageResource = product.imageRes,
                productName = product.name,
                productPrice = product.price,
                onClick = { onProductSelected(product) }
            )
        }
    }
}

@Composable
fun ProductCard(
    cartViewModel: CartViewModel = koinViewModel(),
    imageResource: Int,
    productName: String,
    productPrice: Double,
    onClick: () -> Unit
) {

    Column(
        modifier = Modifier
            .width(150.dp)
            .height(235.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(220.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(Color.White)
                .clickable { onClick() },
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .size(120.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = imageResource),
                        contentDescription = productName,
                        modifier = Modifier
                            .fillMaxSize()
                            .aspectRatio(1f)
                            .clip(RoundedCornerShape(12.dp)),
                        contentScale = ContentScale.Crop
                    )
                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .height(45.dp)
                        .background(Color.LightGray.copy(alpha = 0.5f), RoundedCornerShape(12.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = productName,
                        softWrap = true,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        textAlign = TextAlign.Center,
                        color = Color.Black,
                        modifier = Modifier
                            .padding(horizontal = 10.dp)
                    )
                }
                Text(
                    text = cartViewModel.formatPrice(productPrice),
                    fontSize = 18.sp,
                    color = ColorSec
                )
            }
        }
    }
}