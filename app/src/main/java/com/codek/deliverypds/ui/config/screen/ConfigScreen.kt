package com.codek.deliverypds.ui.config.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.codek.deliverypds.app.theme.Dark
import com.codek.deliverypds.app.theme.ManageStatusBarIcons
import com.codek.deliverypds.ui.config.components.ConfigPhotoPicker
import com.codek.deliverypds.ui.config.viewmodel.ConfigViewModel

@Composable
fun ConfigScreen(
    configViewModel: ConfigViewModel,
    onNavigateToHome: () -> Unit
) {

    // Cor dos ícones da status bar
    ManageStatusBarIcons(isIconBlack = false)

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(30.dp)
                .background(Dark)
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(70.dp)
                .background(Dark)
                .padding(11.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Box {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.95f)
                ) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Sair",
                        tint = Color.White,
                        modifier = Modifier
                            .size(30.dp)
                            .clickable { onNavigateToHome() }
                    )
                }
                Box(
                    modifier = Modifier.fillMaxWidth(0.95f),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Configurações",
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        fontSize = 25.sp
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(24.dp))
        ConfigPhotoPicker(
            configViewModel = configViewModel,
            onNavigateToHome = onNavigateToHome
        )
    }
}