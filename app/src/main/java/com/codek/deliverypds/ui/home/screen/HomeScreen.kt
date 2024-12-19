package com.codek.deliverypds.ui.home.screen

import androidx.activity.ComponentActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowInsetsControllerCompat
import com.codek.deliverypds.app.states.CartState
import com.codek.deliverypds.app.theme.ColorPri
import com.codek.deliverypds.app.theme.ColorSec
import com.codek.deliverypds.app.theme.Dark
import com.codek.deliverypds.app.theme.ManageStatusBarIcons
import com.codek.deliverypds.ui.home.components.HomeCategory
import com.codek.deliverypds.ui.home.components.HomeHeader
import com.codek.deliverypds.ui.home.components.HomeLogo
import com.codek.deliverypds.ui.home.components.HomeProducts
import com.codek.deliverypds.ui.home.components.Product
import com.codek.deliverypds.ui.home.components.ProductDialog
import com.codek.deliverypds.ui.home.components.categories
import com.codek.deliverypds.ui.home.components.products

@Composable
fun HomeScreen(
    onSignOutClick: () -> Unit
) {
    // Cor dos ícones da status bar
    ManageStatusBarIcons(isIconBlack = false)

    val cartState = remember { CartState() }
    var searchText by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf(0) }
    val cartItemCount = cartState.items.sumOf { it.quantity }
    var selectedProduct by remember { mutableStateOf<Product?>(null) }

    val filteredProducts = products.filter {
        (selectedCategory == 0 || it.categoryId == selectedCategory) &&
                it.name.contains(searchText, ignoreCase = true)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.LightGray.copy(alpha = 0.5f))
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(30.dp)
                .background(Dark)
        )

        // Header
        HomeHeader(
            searchText = searchText,
            onSearchTextChanged = { searchText = it },
            cartItemCount = cartItemCount,
            onClicked = { onSignOutClick() }
        )

        // Seção do logo
        HomeLogo(
            colorSec = ColorSec,
            onNavigateToTeste = {  }
        )

        // Seção de Categorias
        HomeCategory(
            colorPri = ColorPri,
            categories = categories,
            selectedCategory = selectedCategory,
            onCategorySelected = { selectedCategory = it }
        )

        // Carrossel de produtos
        HomeProducts(
            colorSec = ColorSec,
            filteredProducts = filteredProducts,
            onProductSelected = { selectedProduct = it }
        )

        // Modal para iserir no carrinho
        selectedProduct?.let { product ->
            ProductDialog(
                product = product,
                colorSec = ColorSec,
                onDismiss = { selectedProduct = null },
                onAddToCart = { quantity ->
                    cartState.addItem(product.id, product.name, product.price, product.imageRes, quantity)
                    selectedProduct = null
                }
            )
        }
    }
}