package com.codek.deliverypds.ui.payment.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.codek.deliverypds.Screen
import com.codek.deliverypds.app.animations.enterTransition
import com.codek.deliverypds.app.animations.exitTransition
import com.codek.deliverypds.app.animations.popEnterTransition
import com.codek.deliverypds.app.animations.popExitTransition
import com.codek.deliverypds.ui.cart.viewmodel.CartViewModel
import com.codek.deliverypds.ui.payment.screen.PaymentScreen

fun NavGraphBuilder.paymentScreen(
    cartViewModel: CartViewModel,
    onNavigateToHome: () -> Unit = {}
) {
    composable(
        route = Screen.Payment.route,
        enterTransition = { enterTransition() },
        exitTransition = { exitTransition() },
        popEnterTransition = { popEnterTransition() },
        popExitTransition = { popExitTransition() }
    ) {

        PaymentScreen(
            cartViewModel = cartViewModel,
            onNavigateToHome = {
                onNavigateToHome()
            }
        )
    }
}