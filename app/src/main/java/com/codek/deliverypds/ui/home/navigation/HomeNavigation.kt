package com.codek.deliverypds.ui.home.navigation

import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.codek.deliverypds.Screen
import com.codek.deliverypds.app.animations.enterTransition
import com.codek.deliverypds.app.animations.exitTransition
import com.codek.deliverypds.app.animations.popEnterTransition
import com.codek.deliverypds.app.animations.popExitTransition
import com.codek.deliverypds.app.repository.AuthRepository
import com.codek.deliverypds.ui.cart.viewmodel.CartViewModel
import com.codek.deliverypds.ui.home.screen.HomeScreen
import com.codek.deliverypds.ui.home.viewmodel.HomeViewModel
import com.codek.deliverypds.ui.login.viewmodel.LoginViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

fun NavGraphBuilder.homeScreen(
    homeViewModel: HomeViewModel,
    cartViewModel: CartViewModel,
    onNavigateToConfig: () -> Unit,
    onSignOut: () -> Unit,
    onNavigateToCart: () -> Unit
) {
    composable(
        route = Screen.Home.route,
        enterTransition = { enterTransition() },
        exitTransition = { exitTransition() },
        popEnterTransition = { popEnterTransition() },
        popExitTransition = { popExitTransition() }
    ) {

        val authRepository = AuthRepository(FirebaseAuth.getInstance())
        val viewModel = LoginViewModel(authRepository)
        val scope = rememberCoroutineScope()

        HomeScreen(
            homeViewModel = homeViewModel,
            cartViewModel = cartViewModel,
            onNavigateToPhoto = {
                onNavigateToConfig()
            },
            onSignOutClick = {
                scope.launch {
                    viewModel.signOut()
                }
                onSignOut()
            },
            onNavigateToCart = {
                onNavigateToCart()
            }
        )
    }
}