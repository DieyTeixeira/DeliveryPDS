package com.codek.deliverypds.ui.config.navigation

import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.codek.deliverypds.Screen
import com.codek.deliverypds.app.animations.enterTransition
import com.codek.deliverypds.app.animations.exitTransition
import com.codek.deliverypds.app.animations.popEnterTransition
import com.codek.deliverypds.app.animations.popExitTransition
import com.codek.deliverypds.app.repository.AuthRepository
import com.codek.deliverypds.ui.config.screen.ConfigScreen
import com.codek.deliverypds.ui.login.viewmodel.LoginViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

fun NavGraphBuilder.configScreen(
    onNavigateToHome: () -> Unit,
    onSignOut: () -> Unit,
    onNavigateToUser: () -> Unit,
    onNavigateToAddress: () -> Unit,
    onNavigateToCategoryEdit: () -> Unit,
    onNavigateToProductEdit: () -> Unit,
    onNavigateToCategoryList: () -> Unit,
    onNavigateToProductList: () -> Unit
) {
    composable(
        route = Screen.Config.route,
        enterTransition = { enterTransition() },
        exitTransition = { exitTransition() },
        popEnterTransition = { popEnterTransition() },
        popExitTransition = { popExitTransition() }
    ) {

        val authRepository = AuthRepository(FirebaseAuth.getInstance())
        val viewModel = LoginViewModel(authRepository)
        val scope = rememberCoroutineScope()

        ConfigScreen(
            onNavigateToHome = {
                onNavigateToHome()
            },
            onSignOutClick = {
                scope.launch {
                    viewModel.signOut()
                }
                onSignOut()
            },
            onButtonClick = {
                when (it) {
                    "Usuário" -> {
                        onNavigateToUser()
                    }
                    "Endereços" -> {
                        onNavigateToAddress()
                    }
                    "CategoriasEdit" -> {
                        onNavigateToCategoryEdit()
                    }
                    "ProdutosEdit" -> {
                        onNavigateToProductEdit()
                    }
                    "CategoriasList" -> {
                        onNavigateToCategoryList()
                    }
                    "ProdutosList" -> {
                        onNavigateToProductList()
                    }
                }
            }
        )

    }
}