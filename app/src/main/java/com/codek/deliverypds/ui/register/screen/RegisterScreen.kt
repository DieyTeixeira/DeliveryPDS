package com.codek.deliverypds.ui.register.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun RegisterScreen(
    onButtonClick: (String) -> Unit
) {

    val destinations = listOf(
        "Usuário",
        "Categorias",
        "Produtos",
        "Endereços"
    )

    LazyVerticalGrid(
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp, 0.dp),
        columns = GridCells.Fixed(2),
        verticalArrangement = Arrangement.spacedBy(15.dp),
        horizontalArrangement = Arrangement.spacedBy(15.dp)
    ) {
        items(destinations.size) { index ->
            val name = destinations[index]
            RegisterCard(name = name, onClick = { onButtonClick(name) })
        }
    }
}

@Composable
fun RegisterCard(name: String, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .width(150.dp)
            .padding(10.dp)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = name
        )
    }
}