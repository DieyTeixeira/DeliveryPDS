package com.codek.deliverypds.ui.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.codek.deliverypds.R

@Composable
fun HomeLogo(
    colorSec: Color,
    onNavigateToTeste: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
            .background(colorSec),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo_panelinha),
            contentDescription = "Logo",
            modifier = Modifier
                .size(100.dp)
                .clip(RoundedCornerShape(100))
                .clickable( onClick = onNavigateToTeste )
        )
    }
}