package com.codek.deliverypds.ui.config.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.codek.deliverypds.app.configs.BarNavigation
import com.codek.deliverypds.app.theme.Dark
import com.codek.deliverypds.app.theme.ManageStatusBarIcons
import com.codek.deliverypds.ui.config.components.ConfigHeader
import com.codek.deliverypds.ui.config.components.ConfigPhotoPicker
import com.codek.deliverypds.ui.config.viewmodel.ConfigViewModel

@Composable
fun ConfigScreen(
    configViewModel: ConfigViewModel,
    onNavigateToHome: () -> Unit,
    onSignOutClick: () -> Unit
) {

    // Cor dos ícones da status bar
    ManageStatusBarIcons(isIconBlack = false)

    BarNavigation(
        onNavigateToHome = { onNavigateToHome() },
        onNavigateToConfig = { },
        onSignOut = { onSignOutClick() }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.LightGray.copy(alpha = 0.5f)),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(30.dp)
                    .background(Dark)
            )

            ConfigHeader()

            Spacer(modifier = Modifier.height(24.dp))
            ConfigPhotoPicker(
                configViewModel = configViewModel,
                onNavigateToHome = onNavigateToHome
            )
        }
    }
}