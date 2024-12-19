package com.codek.deliverypds.ui.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun HomeCategory(
    colorPri: Color,
    categories: List<String>,
    selectedCategory: Int,
    onCategorySelected: (Int) -> Unit = {}
) {

    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp, bottom = 5.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        items(categories.size) { index ->
            if (index != 0) {
                Spacer(modifier = Modifier.width(15.dp))
            }
            CategoryButton(
                colorPri = colorPri,
                categoryName = categories[index],
                isSelected = selectedCategory == index,
                onClick = { onCategorySelected(index) }
            )
        }
    }
}

@Composable
fun CategoryButton(
    colorPri: Color,
    categoryName: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(50))
            .background(if (isSelected) colorPri else Color.Gray.copy(alpha = 0.5f))
            .size(59.dp)
            .clickable { onClick() },
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = categoryName,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier.padding(8.dp)
        )
    }
}