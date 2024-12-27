package com.codek.deliverypds.ui.registers.user.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.codek.deliverypds.app.configs.BarNavigation
import com.codek.deliverypds.app.theme.ColorError
import com.codek.deliverypds.app.theme.ColorSec
import com.codek.deliverypds.app.theme.ColorSucess
import com.codek.deliverypds.app.theme.DarkColorError
import com.codek.deliverypds.app.theme.DarkColorSucess
import com.codek.deliverypds.ui.registers.components.FieldTextNumber
import com.codek.deliverypds.ui.registers.components.FieldTextString
import com.codek.deliverypds.ui.registers.components.RegistersHeader
import com.codek.deliverypds.ui.registers.components.RegistersLoading
import com.codek.deliverypds.ui.registers.components.RegistersMessage
import com.codek.deliverypds.ui.registers.components.formatCpfMask
import com.codek.deliverypds.ui.registers.components.formatPhoneMask
import com.codek.deliverypds.ui.registers.user.state.MessageUserState
import com.codek.deliverypds.ui.registers.user.viewmodel.UserViewModel

@Composable
fun UserScreen(
    userViewModel: UserViewModel,
    onNavigateToHome: () -> Unit,
    onNavigateToConfig: () -> Unit,
    onSignOutClick: () -> Unit
) {

    val userState = userViewModel.userState.collectAsState()
    val message = userViewModel.message.collectAsState()

    LaunchedEffect(Unit) {
        userViewModel.findUser()
    }

    BarNavigation(
        onNavigateToHome = { onNavigateToHome() },
        onNavigateToConfig = { onNavigateToConfig() },
        onSignOut = { onSignOutClick() }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.LightGray.copy(alpha = 0.5f)),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            RegistersHeader("Config. Usuário")

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(15.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth(0.85f)
                        .weight(1f)
                        .padding(top = 15.dp),
                    verticalArrangement = Arrangement.spacedBy(15.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    FieldTextString(
                        text = userState.value.name,
                        onTextChange = { userViewModel.updateName(it) },
                        placeHolder = "Nome"
                    )
                    FieldTextNumber(
                        text = formatCpfMask(userState.value.cpf),
                        onTextChange = { userViewModel.updateCpf(it) },
                        placeHolder = "CPF"
                    )
                    FieldTextNumber(
                        text = formatPhoneMask(userState.value.phone),
                        onTextChange = { userViewModel.updatePhone(it) },
                        placeHolder = "Telefone"
                    )
                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp),
                    contentAlignment = Alignment.Center
                ) {
                    when {
                        userViewModel.isLoading.collectAsState().value -> RegistersLoading(Color.DarkGray)
                        message.value is MessageUserState.Success -> RegistersMessage(
                            message = (message.value as MessageUserState.Success).message,
                            color = Pair(ColorSucess, DarkColorSucess),
                            onNavigateToConfig = onNavigateToConfig
                        )
                        message.value is MessageUserState.Error -> RegistersMessage(
                            message = (message.value as MessageUserState.Error).message,
                            color = Pair(ColorError, DarkColorError),
                            onNavigateToConfig = { }
                        )
                    }
                }
                Spacer(modifier = Modifier.height(15.dp))
                if (userViewModel.isLoading.collectAsState().value) {
                    Box(
                        modifier = Modifier
                            .width(175.dp)
                            .height(40.dp)
                            .background(
                                Color.LightGray,
                                RoundedCornerShape(16.dp)
                            )
                            .clickable(
                                indication = null,
                                interactionSource = remember { MutableInteractionSource() }
                            ) {  },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Salvar",
                            color = Color.White
                        )
                    }
                } else {
                    Box(
                        modifier = Modifier
                            .width(175.dp)
                            .height(40.dp)
                            .background(
                                ColorSec,
                                RoundedCornerShape(16.dp)
                            )
                            .clickable(
                                indication = null,
                                interactionSource = remember { MutableInteractionSource() }
                            ) { userViewModel.salvarUser() },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Salvar",
                            color = Color.White
                        )
                    }
                }
                Spacer(modifier = Modifier.height(55.dp))
            }
        }
    }
}