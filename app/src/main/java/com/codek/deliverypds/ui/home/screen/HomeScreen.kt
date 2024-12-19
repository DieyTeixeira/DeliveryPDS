package com.codek.deliverypds.ui.home.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.codek.deliverypds.app.theme.Dark
import com.codek.deliverypds.app.theme.ManageStatusBarIcons
import com.codek.deliverypds.ui.cart.viewmodel.CartViewModel
import com.codek.deliverypds.ui.home.components.HomeCategory
import com.codek.deliverypds.ui.home.components.HomeHeader
import com.codek.deliverypds.ui.home.components.HomeLogo
import com.codek.deliverypds.ui.home.components.HomeProductDialog
import com.codek.deliverypds.ui.home.components.HomeProducts
import com.codek.deliverypds.ui.home.viewmodel.HomeViewModel

@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel,
    cartViewModel: CartViewModel,
    onSignOutClick: () -> Unit,
    onNavigateToCart: () -> Unit
) {
    // Cor dos ícones da status bar
    ManageStatusBarIcons(isIconBlack = false)

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
            searchText = homeViewModel.searchText,
            onSearchTextChanged = { homeViewModel.searchText = it },
            cartItemCount = cartViewModel.cartItemCount(),
            onClicked = { onNavigateToCart() }
        )

        // Seção do logo
        HomeLogo(
            onSignOutClick = { onSignOutClick() }
        )

        // Seção de Categorias
        HomeCategory(
            categories = homeViewModel.category,
            selectedCategory = homeViewModel.selectedCategory,
            onCategorySelected = { homeViewModel.selectedCategory = it }
        )

        // Carrossel de produtos
        HomeProducts(
            filteredProducts = homeViewModel.filteredProducts,
            onProductSelected = { homeViewModel.selectedProduct = it }
        )

        // Modal para iserir no carrinho
        homeViewModel.selectedProduct?.let { product ->
            HomeProductDialog(
                product = product,
                cartViewModel = cartViewModel,
                onDismiss = { homeViewModel.selectedProduct = null },
                onAddToCart = { quantity ->
                    cartViewModel.addItem(product.id, product.name, product.price, product.imageRes, quantity)
                    homeViewModel.selectedProduct = null
                }
            )
        }
    }
}