package com.codek.deliverypds.ui.login.navigation

import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.codek.deliverypds.Screen
import com.codek.deliverypds.app.animations.enterTransition
import com.codek.deliverypds.app.animations.exitTransition
import com.codek.deliverypds.app.animations.popEnterTransition
import com.codek.deliverypds.app.animations.popExitTransition
import com.codek.deliverypds.ui.login.screen.LoginScreen
import com.codek.deliverypds.ui.login.viewmodel.LoginViewModel
import kotlinx.coroutines.launch

fun NavGraphBuilder.loginScreen(
    loginViewModel: LoginViewModel,
    onLoginSuccess: () -> Unit
) {
    composable(
        route = Screen.Login.route,
        enterTransition = { enterTransition() },
        exitTransition = { exitTransition() },
        popEnterTransition = { popEnterTransition() },
        popExitTransition = { popExitTransition() }
    ) {

        val scope = rememberCoroutineScope()

        LoginScreen(
            loginViewModel = loginViewModel,
            onSignInClick = {
                scope.launch {
                    loginViewModel.signIn()
                }
            },
            onSignUpClick = {
                scope.launch {
                    loginViewModel.signUp()
                }
            },
            onLoginSuccess = onLoginSuccess
        )
    }
}