package com.codek.deliverypds.ui.payment.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.codek.deliverypds.R
import com.codek.deliverypds.app.configs.BarNavigation
import com.codek.deliverypds.app.configs.SendMessageToWhatsApp
import com.codek.deliverypds.app.configs.generateCartMessage
import com.codek.deliverypds.app.theme.ColorSec
import com.codek.deliverypds.ui.cart.viewmodel.CartViewModel
import com.codek.deliverypds.ui.payment.components.PaymentClient
import com.codek.deliverypds.ui.payment.components.PaymentHeader

@Composable
fun PaymentScreen(
    cartViewModel: CartViewModel,
    onNavigateToHome: () -> Unit = {}
) {

    val context = LocalContext.current
    val tipoPagamento = remember { mutableStateOf("") }

    var name by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var adress by remember { mutableStateOf("") }
    var numberHome by remember { mutableStateOf("") }
    var complement by remember { mutableStateOf("") }
    var district by remember { mutableStateOf("") }
    var city by remember { mutableStateOf("") }

    BarNavigation(
        onNavigateToHome = { onNavigateToHome() },
        onNavigateToConfig = { },
        onSignOut = { }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.LightGray.copy(alpha = 0.5f)),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Header
            PaymentHeader()

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(15.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                ) {
                    // Dados do Cliente
                    PaymentClient(
                        name = name,
                        onNameChange = { newName -> name = newName },
                        phone = phone,
                        onPhoneChange = { newPhone -> phone = newPhone },
                        address = adress,
                        onAdressChange = { newAdress -> adress = newAdress },
                        numberHome = numberHome,
                        onNumberHomeChange = { newNumberHome -> numberHome = newNumberHome },
                        complement = complement,
                        onComplementChange = { newComplement -> complement = newComplement },
                        district = district,
                        onDistrictChange = { newDistrict -> district = newDistrict },
                        city = city,
                        onCityChange = { newCity -> city = newCity }
                    )

                    Spacer(modifier = Modifier.height(15.dp))

                    Text(
                        text = "Escolha o método de pagamento:",
                        fontSize = 18.sp,
                        color = Color.DarkGray,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(20.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(75.dp)
                                    .border(
                                        5.dp,
                                        if (tipoPagamento.value == "Pix") ColorSec else Color.Transparent,
                                        RoundedCornerShape(100)
                                    )
                                    .clickable {
                                        tipoPagamento.value = "Pix"
                                    },
                                contentAlignment = Alignment.Center
                            ) {
                                PaymentOption(icon = painterResource(id = R.drawable.ic_pix))
                            }
                            Box(
                                modifier = Modifier
                                    .size(75.dp)
                                    .border(
                                        5.dp,
                                        if (tipoPagamento.value == "Cartão de Crédito/Débito") ColorSec else Color.Transparent,
                                        RoundedCornerShape(100)
                                    )
                                    .clickable {
                                        tipoPagamento.value = "Cartão de Crédito/Débito"
                                    },
                                contentAlignment = Alignment.Center
                            ) {
                                PaymentOption(icon = painterResource(id = R.drawable.ic_cartao))
                            }
                            Box(
                                modifier = Modifier
                                    .size(75.dp)
                                    .border(
                                        5.dp,
                                        if (tipoPagamento.value == "Dinheiro") ColorSec else Color.Transparent,
                                        RoundedCornerShape(100)
                                    )
                                    .clickable {
                                        tipoPagamento.value = "Dinheiro"
                                    },
                                contentAlignment = Alignment.Center
                            ) {
                                PaymentOption(icon = painterResource(id = R.drawable.ic_dinheiro))
                            }
                        }
                    }
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            Color.LightGray,
                            RoundedCornerShape(16.dp)
                        )
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Total do Carrinho:",
                            modifier = Modifier.padding(16.dp),
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = cartViewModel.formatPrice(cartViewModel.totalCartPrice()),
                            modifier = Modifier.padding(16.dp),
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
                Spacer(modifier = Modifier.height(15.dp))

                // Botões de Confirmar e Cancelar
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
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
                            ) { onNavigateToHome() },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Cancelar",
                            color = Color.White,
                            fontSize = 16.sp
                        )
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    if (cartViewModel.items.isNotEmpty()) {
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
                                ) {
                                    val message = generateCartMessage(
                                        cartViewModel = cartViewModel,
                                        tipoPagamento = tipoPagamento.value
                                    )
                                    SendMessageToWhatsApp(
                                        context = context,
                                        phoneNumber = "5551992189353",
                                        message = message
                                    )
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "Finalizar Pedido",
                                color = Color.White,
                                fontSize = 16.sp
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(55.dp))
            }
        }
    }
}

@Composable
fun PaymentOption(icon: Painter) {
    Box(
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = icon,
            contentDescription = null,
            modifier = Modifier
                .size(70.dp)
        )
    }
}