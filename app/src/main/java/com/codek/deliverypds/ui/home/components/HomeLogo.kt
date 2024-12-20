package com.codek.deliverypds.ui.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.codek.deliverypds.R
import com.codek.deliverypds.app.theme.ColorSec

@Composable
fun HomeLogo(
    onSignOutClick: () -> Unit,
    onPhotoClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
            .background(ColorSec),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(0.95f)
                .height(100.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Icon(
                imageVector = Icons.Filled.ArrowBack,
                contentDescription = "Sair",
                tint = Color.White,
                modifier = Modifier
                    .size(30.dp)
                    .clickable { onSignOutClick() }
            )
            Image(
                painter = painterResource(id = R.drawable.logo_pds),
                contentDescription = "Logo",
                modifier = Modifier
                    .size(100.dp)
                    .clip(RoundedCornerShape(100))
            )
            Icon(
                imageVector = Icons.Outlined.Settings,
                contentDescription = "Configuração",
                tint = Color.White,
                modifier = Modifier
                    .size(30.dp)
                    .clickable { onPhotoClick() }
            )
        }
    }
}

@Preview
@Composable
private fun HomeLogoPreview() {
    HomeLogo(
        onSignOutClick = {},
        onPhotoClick = {}
    )
}