package com.codek.deliverypds.ui.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults.cardElevation
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.codek.deliverypds.app.states.formatPrice

@Composable
fun HomeProducts(
    colorSec: Color,
    filteredProducts: List<Product>,
    onProductSelected: (Product) -> Unit = {}
) {
    LazyVerticalGrid(
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp),
        columns = GridCells.Fixed(2),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(filteredProducts.size) { index ->
            val product = filteredProducts[index]
            ProductCard(
                imageResource = product.imageRes,
                productName = product.name,
                productPrice = product.price,
                colorSec = colorSec,
                onClick = { onProductSelected(product) }
            )
        }
    }
}

@Composable
fun ProductCard(
    imageResource: Int,
    productName: String,
    productPrice: Double,
    colorSec: Color,
    onClick: () -> Unit
) {

    Box(
        modifier = Modifier
            .width(150.dp)
            .height(220.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(Color.White)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = imageResource),
                contentDescription = productName,
                modifier = Modifier
                    .size(120.dp)
                    .clip(RoundedCornerShape(12.dp))
            )
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
                text = formatPrice(productPrice),
                fontSize = 18.sp,
                color = colorSec
            )
        }
    }
}