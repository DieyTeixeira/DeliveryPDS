package com.codek.deliverypds.ui.registers.address.navigation

import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.codek.deliverypds.Screen
import com.codek.deliverypds.app.animations.enterTransition
import com.codek.deliverypds.app.animations.exitTransition
import com.codek.deliverypds.app.animations.popEnterTransition
import com.codek.deliverypds.app.animations.popExitTransition
import com.codek.deliverypds.app.repository.AuthRepository
import com.codek.deliverypds.ui.login.viewmodel.LoginViewModel
import com.codek.deliverypds.ui.registers.address.screen.AddressScreen
import com.codek.deliverypds.ui.registers.address.viewmodel.AddressViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

fun NavGraphBuilder.addressScreen(
    addressViewModel: AddressViewModel,
    onNavigateToHome: () -> Unit,
    onNavigateToConfig: () -> Unit,
    onSignOut: () -> Unit
) {
    composable(
        route = Screen.Address.route,
        enterTransition = { enterTransition() },
        exitTransition = { exitTransition() },
        popEnterTransition = { popEnterTransition() },
        popExitTransition = { popExitTransition() }
    ) {

        val authRepository = AuthRepository(FirebaseAuth.getInstance())
        val viewModel = LoginViewModel(authRepository)
        val scope = rememberCoroutineScope()

        AddressScreen(
            addressViewModel = addressViewModel,
            onNavigateToHome = {
                onNavigateToHome()
            },
            onNavigateToConfig = {
                onNavigateToConfig()
            },
            onSignOutClick = {
                scope.launch {
                    viewModel.signOut()
                }
                onSignOut()
            }
        )

    }
}