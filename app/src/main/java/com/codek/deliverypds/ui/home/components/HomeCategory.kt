package com.codek.deliverypds.ui.home.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.codek.deliverypds.app.theme.ColorPri
import com.codek.deliverypds.ui.home.state.CategoryUrl
import coil.compose.rememberImagePainter
import com.codek.deliverypds.app.theme.ColorSec
import com.codek.deliverypds.ui.home.state.Category

@Composable
fun HomeCategory(
    categories: List<Category>,
    selectedCategory: Int,
    onCategorySelected: (Int) -> Unit = {}
) {

    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 5.dp, bottom = 2.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        items(categories.size) { index ->
            if (index != 0) {
                Spacer(modifier = Modifier.width(15.dp))
            }
            CategoryButton(
                category = categories[index],
                isSelected = selectedCategory == categories[index].id,
                onClick = { onCategorySelected(categories[index].id) }
            )
        }
    }
}

@Composable
fun CategoryButton(
    category: Category,
    isSelected: Boolean,
    onClick: () -> Unit
) {

    val backgroundColor by animateColorAsState(
        targetValue = if (isSelected) ColorPri else Color.LightGray,
        animationSpec = tween(durationMillis = 400)
    )

    val indicatorWidth by animateDpAsState(
        targetValue = if (isSelected) 50.dp else 0.dp,
        animationSpec = tween(durationMillis = 500)
    )

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier
                .clip(RoundedCornerShape(50))
                .background(backgroundColor)
                .size(59.dp)
                .clickable { onClick() },
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = rememberImagePainter(data = category.imageRes),
                contentDescription = category.name,
                modifier = Modifier.size(35.dp)
            )
        }
        Text(
            text = category.name,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            color = Color.DarkGray
        )
        Box(
            modifier = Modifier
                .width(indicatorWidth)
                .height(2.dp)
                .background(if (isSelected) ColorPri else Color.Transparent)
        )
    }
}