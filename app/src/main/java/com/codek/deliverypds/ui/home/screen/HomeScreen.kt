package com.codek.deliverypds.ui.home.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomAppBar
import androidx.compose.material.BottomNavigation
import androidx.compose.material.FabPosition
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.FloatingActionButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Brush
import androidx.compose.material.icons.outlined.CheckBox
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.MicNone
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.codek.deliverypds.app.barnavigation.BottomBarNavigation
import com.codek.deliverypds.app.theme.ColorPri
import com.codek.deliverypds.app.theme.ColorSec
import com.codek.deliverypds.app.theme.Dark
import com.codek.deliverypds.app.theme.ManageStatusBarIcons
import com.codek.deliverypds.ui.cart.viewmodel.CartViewModel
import com.codek.deliverypds.ui.home.components.HomeCategory
import com.codek.deliverypds.ui.home.components.HomeHeader
import com.codek.deliverypds.ui.home.components.HomeLogo
import com.codek.deliverypds.ui.home.components.HomeProductDialog
import com.codek.deliverypds.ui.home.components.HomeProducts
import com.codek.deliverypds.ui.home.viewmodel.HomeViewModel
import kotlin.math.roundToInt

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
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(30.dp)
                        .background(Dark)
                )

                // Header
                HomeHeader(
                    searchText = homeViewModel.searchText,
                    onSearchTextChanged = { homeViewModel.searchText = it }
                )

//                // Seção do logo
//                HomeLogo(
//                    onSignOutClick = { onSignOutClick() },
//                    onPhotoClick = { onNavigateToPhoto() }
//                )

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