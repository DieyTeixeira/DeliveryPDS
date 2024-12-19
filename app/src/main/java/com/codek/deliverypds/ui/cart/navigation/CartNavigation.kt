package com.codek.deliverypds.ui.cart.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.codek.deliverypds.Screen
import com.codek.deliverypds.app.animations.enterTransition
import com.codek.deliverypds.app.animations.exitTransition
import com.codek.deliverypds.app.animations.popEnterTransition
import com.codek.deliverypds.app.animations.popExitTransition
import com.codek.deliverypds.ui.cart.screen.CartScreen
import com.codek.deliverypds.ui.cart.viewmodel.CartViewModel

fun NavGraphBuilder.cartScreen(
    cartViewModel: CartViewModel,
    onNavigateToHome: () -> Unit
) {
    composable(
        route = Screen.Cart.route,
        enterTransition = { enterTransition() },
        exitTransition = { exitTransition() },
        popEnterTransition = { popEnterTransition() },
        popExitTransition = { popExitTransition() }
    ) {

        CartScreen(
            cartViewModel = cartViewModel,
            onNavigateToHome = {
                onNavigateToHome()
            }
        )
    }
}