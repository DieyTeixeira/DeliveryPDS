package com.codek.deliverypds.ui.registers.address.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
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
import com.codek.deliverypds.ui.registers.address.state.MessageAddressState
import com.codek.deliverypds.ui.registers.address.viewmodel.AddressViewModel
import com.codek.deliverypds.ui.registers.components.FieldTextNumber
import com.codek.deliverypds.ui.registers.components.FieldTextString
import com.codek.deliverypds.ui.registers.components.RegistersHeader
import com.codek.deliverypds.ui.registers.components.RegistersLoading
import com.codek.deliverypds.ui.registers.components.RegistersMessage
import com.codek.deliverypds.ui.registers.components.formatCepMask

@Composable
fun AddressScreen(
    addressViewModel: AddressViewModel,
    onNavigateToHome: () -> Unit,
    onNavigateToConfig: () -> Unit,
    onSignOutClick: () -> Unit
) {

    val addressState = addressViewModel.addressState.collectAsState()
    val message = addressViewModel.message.collectAsState()

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
            RegistersHeader("Cadastro Endereço")

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
                        text = addressState.value.street,
                        onTextChange = { addressViewModel.updateStreet(it) },
                        placeHolder = "Rua"
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(15.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .weight(1f),
                            contentAlignment = Alignment.Center
                        ) {
                            FieldTextNumber(
                                text = addressState.value.number,
                                onTextChange = { newValue ->
                                    if (newValue.all { it.isDigit() }) {
                                        addressViewModel.updateNumber(newValue)
                                    }
                                },
                                placeHolder = "Número"
                            )
                        }
                        Box(
                            modifier = Modifier
                                .weight(2f),
                            contentAlignment = Alignment.Center
                        ) {
                            FieldTextString(
                                text = addressState.value.complement,
                                onTextChange = { addressViewModel.updateComplement(it) },
                                placeHolder = "Complemento"
                            )
                        }
                    }
                    FieldTextNumber(
                        text = formatCepMask(addressState.value.cep),
                        onTextChange = { addressViewModel.updateCep(it) },
                        placeHolder = "CEP"
                    )
                    FieldTextString(
                        text = addressState.value.district,
                        onTextChange = { addressViewModel.updateDistrict(it) },
                        placeHolder = "Bairro"
                    )
                    FieldTextString(
                        text = addressState.value.city,
                        onTextChange = { addressViewModel.updateCity(it) },
                        placeHolder = "Cidade"
                    )
                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp),
                    contentAlignment = Alignment.Center
                ) {
                    when {
                        addressViewModel.isLoading.collectAsState().value -> RegistersLoading(Color.DarkGray)
                        message.value is MessageAddressState.Success -> RegistersMessage(
                            message = (message.value as MessageAddressState.Success).message,
                            color = Pair(ColorSucess, DarkColorSucess),
                            onNavigateToConfig = onNavigateToConfig
                        )
                        message.value is MessageAddressState.Error -> RegistersMessage(
                            message = (message.value as MessageAddressState.Error).message,
                            color = Pair(ColorError, DarkColorError),
                            onNavigateToConfig = { }
                        )
                    }
                }
                Spacer(modifier = Modifier.height(15.dp))
                if (addressViewModel.isLoading.collectAsState().value) {
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
                            ) { addressViewModel.salvarAddress() },
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