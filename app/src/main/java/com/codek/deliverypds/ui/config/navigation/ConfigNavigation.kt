package com.codek.deliverypds.ui.config.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.codek.deliverypds.Screen
import com.codek.deliverypds.app.animations.enterTransition
import com.codek.deliverypds.app.animations.exitTransition
import com.codek.deliverypds.app.animations.popEnterTransition
import com.codek.deliverypds.app.animations.popExitTransition
import com.codek.deliverypds.ui.config.screen.ConfigScreen
import com.codek.deliverypds.ui.config.viewmodel.ConfigViewModel

fun NavGraphBuilder.configScreen(
    configViewModel: ConfigViewModel,
    onNavigateToHome: () -> Unit
) {
    composable(
        route = Screen.Config.route,
        enterTransition = { enterTransition() },
        exitTransition = { exitTransition() },
        popEnterTransition = { popEnterTransition() },
        popExitTransition = { popExitTransition() }
    ) {

        ConfigScreen(
            configViewModel = configViewModel,
            onNavigateToHome = {
                onNavigateToHome()
            }
        )

    }
}