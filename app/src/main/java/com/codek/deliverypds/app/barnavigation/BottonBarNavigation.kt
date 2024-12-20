package com.codek.deliverypds.app.barnavigation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.Brush
import androidx.compose.material.icons.outlined.CheckBox
import androidx.compose.material.icons.outlined.Image
import androidx.compose.material.icons.outlined.MicNone
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.sp
import kotlin.math.roundToInt


@Composable
fun OnlyBottomBarScreen() {
    val primaryColor = Color(0xFF6200EE) // Cor principal da Bottom Bar

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { /* Ação do botão flutuante */ },
                backgroundColor = primaryColor,
                contentColor = Color.White
            ) {
                Icon(Icons.Filled.Add, contentDescription = "Add")
            }
        },
        isFloatingActionButtonDocked = true,
        floatingActionButtonPosition = FabPosition.End,
        bottomBar = {
            BottomAppBar(
                modifier = Modifier
                    .height(55.dp),
                cutoutShape = RoundedCornerShape(50),
                backgroundColor = primaryColor
            ) {
                BottomNavigation {
                    IconButton(onClick = { /* Ação do botão 1 */ }) {
                        Icon(Icons.Outlined.CheckBox, contentDescription = "Checkbox", tint = Color.White)
                    }
                    IconButton(onClick = { /* Ação do botão 2 */ }) {
                        Icon(Icons.Outlined.Brush, contentDescription = "Brush", tint = Color.White)
                    }
                    IconButton(onClick = { /* Ação do botão 3 */ }) {
                        Icon(Icons.Outlined.MicNone, contentDescription = "Mic", tint = Color.White)
                    }
                    IconButton(onClick = { /* Ação do botão 4 */ }) {
                        Icon(Icons.Outlined.Image, contentDescription = "Image", tint = Color.White)
                    }
                }
            }
        },
        content = {} // Sem conteúdo central
    )
}