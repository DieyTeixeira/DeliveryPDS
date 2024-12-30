package com.codek.deliverypds.ui.config.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.ListAlt
import androidx.compose.material.icons.outlined.ShoppingBasket
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.codek.deliverypds.app.configs.BarNavigation
import com.codek.deliverypds.app.theme.ManageStatusBarIcons
import com.codek.deliverypds.ui.config.components.ConfigButtons
import com.codek.deliverypds.ui.config.components.ConfigHeader

@Composable
fun ConfigScreen(
    onNavigateToHome: () -> Unit,
    onSignOutClick: () -> Unit,
    onButtonClick: (String) -> Unit
) {

    // Cor dos ícones da status bar
    ManageStatusBarIcons(isIconBlack = false)

    val destinations = listOf(
        "Usuário" to Icons.Outlined.AccountCircle,
        "Endereços" to Icons.Outlined.Home,
        "CategoriasEdit" to Icons.Outlined.ListAlt,
        "ProdutosEdit" to Icons.Outlined.ShoppingBasket,
        "CategoriasList" to Icons.Outlined.ListAlt,
        "ProdutosList" to Icons.Outlined.ShoppingBasket
    )

    BarNavigation(
        onNavigateToHome = { onNavigateToHome() },
        onNavigateToConfig = { },
        onSignOut = { onSignOutClick() }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.LightGray.copy(alpha = 0.5f)),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            ConfigHeader()

            Spacer(modifier = Modifier.height(15.dp))

            LazyVerticalGrid(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                columns = GridCells.Fixed(2),
                verticalArrangement = Arrangement.spacedBy(15.dp),
                horizontalArrangement = Arrangement.spacedBy(15.dp)
            ) {
                items(destinations.size) { index ->
                    val (name, icon) = destinations[index]
                    ConfigButtons(name = name, icon = icon, onClick = { onButtonClick(name) })
                }
            }

        }
    }
}