package com.codek.deliverypds.ui.home.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.codek.deliverypds.app.configs.BottomBarNavigation
import com.codek.deliverypds.app.theme.Dark
import com.codek.deliverypds.app.theme.ManageStatusBarIcons
import com.codek.deliverypds.ui.cart.viewmodel.CartViewModel
import com.codek.deliverypds.ui.home.components.HomeCategory
import com.codek.deliverypds.ui.home.components.HomeHeader
import com.codek.deliverypds.ui.home.components.HomeProductDialog
import com.codek.deliverypds.ui.home.components.HomeProducts
import com.codek.deliverypds.ui.home.viewmodel.HomeViewModel

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel,
    cartViewModel: CartViewModel,
    onSignOutClick: () -> Unit,
    onNavigateToCart: () -> Unit,
    onNavigateToPhoto: () -> Unit
) {

    // Cor dos ícones da status bar
    ManageStatusBarIcons(isIconBlack = false)

    BottomBarNavigation(
        onNavigateToHome = { },
        onNavigateToConfig = { onNavigateToPhoto() },
        onSignOut = { onSignOutClick() },
        onNavigateToCart = { onNavigateToCart() },
        cartItemCount = cartViewModel.cartItemCount()
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.LightGray.copy(alpha = 0.5f))
            ) {
                // Header
                HomeHeader(
                    searchText = homeViewModel.searchText,
                    onSearchTextChanged = { homeViewModel.searchText = it }
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
            }

            // Modal para iserir no carrinho
            homeViewModel.selectedProduct?.let { product ->
                val isVisible = remember { mutableStateOf(true) }

                LaunchedEffect(homeViewModel.selectedProduct) {
                    isVisible.value = true
                }

                HomeProductDialog(
                    product = product,
                    cartViewModel = cartViewModel,
                    onDismiss = {
                        homeViewModel.selectedProduct = null
                    },
                    onAddToCart = { quantity ->
                        cartViewModel.addItem(
                            product.id,
                            product.name,
                            product.price,
                            product.imageRes,
                            quantity
                        )
                        isVisible.value = false
                    },
                    isVisible = isVisible.value
                )
            }
        }
    }
}