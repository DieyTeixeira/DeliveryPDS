package com.codek.deliverypds.ui.login.navigation

import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.codek.deliverypds.Screen
import com.codek.deliverypds.app.animations.enterTransition
import com.codek.deliverypds.app.animations.exitTransition
import com.codek.deliverypds.app.animations.popEnterTransition
import com.codek.deliverypds.app.animations.popExitTransition
import com.codek.deliverypds.app.repository.AuthRepository
import com.codek.deliverypds.ui.login.screen.LoginScreen
import com.codek.deliverypds.ui.login.viewmodel.LoginViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

fun NavGraphBuilder.loginScreen(
    onLoginSuccess: () -> Unit
) {
    composable(
        route = Screen.Login.route,
        enterTransition = { enterTransition() },
        exitTransition = { exitTransition() },
        popEnterTransition = { popEnterTransition() },
        popExitTransition = { popExitTransition() }
    ) {

        val authRepository = AuthRepository(FirebaseAuth.getInstance())
        val viewModel = LoginViewModel(authRepository)
        val scope = rememberCoroutineScope()

        LoginScreen(
            viewModel = viewModel,
            onSignInClick = {
                scope.launch {
                    viewModel.signIn()
                }
            },
            onSignUpClick = {
                scope.launch {
                    viewModel.signUp()
                }
            },
            onLoginSuccess = onLoginSuccess
        )
    }
}