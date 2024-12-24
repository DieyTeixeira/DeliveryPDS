package com.codek.deliverypds.ui.payment.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun PaymentClient(
    name: String,
    onNameChange: (String) -> Unit,
    phone: String,
    onPhoneChange: (String) -> Unit,
    address: String,
    onAdressChange: (String) -> Unit,
    numberHome: String,
    onNumberHomeChange: (String) -> Unit,
    complement: String,
    onComplementChange: (String) -> Unit,
    district: String,
    onDistrictChange: (String) -> Unit,
    city: String,
    onCityChange: (String) -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = "Dados do Cliente:",
            fontSize = 18.sp,
            color = Color.DarkGray
        )
        // Nome
        FieldTextClient(
            modifier = Modifier.fillMaxWidth(),
            text = name,
            onTextChange = onNameChange,
            placeHolder = "Nome Completo"
        )
        // Telefone
        FieldTextClient(
            modifier = Modifier.fillMaxWidth(),
            text = phone,
            onTextChange = onPhoneChange,
            placeHolder = "Celular"
        )
        // Endereço
        FieldTextClient(
            modifier = Modifier.fillMaxWidth(),
            text = address,
            onTextChange = onAdressChange,
            placeHolder = "Endereço"
        )
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Número da Casa
            FieldTextClient(
                modifier = Modifier.weight(1f),
                text = numberHome,
                onTextChange = onNumberHomeChange,
                placeHolder = "Número"
            )
            // Complemento
            FieldTextClient(
                modifier = Modifier.weight(3f),
                text = complement,
                onTextChange = onComplementChange,
                placeHolder = "Complemento"
            )
        }
        // Bairro
        FieldTextClient(
            modifier = Modifier.fillMaxWidth(),
            text = district,
            onTextChange = onDistrictChange,
            placeHolder = "Bairro"
        )
        // Cidade
        FieldTextClient(
            modifier = Modifier.fillMaxWidth(),
            text = city,
            onTextChange = onCityChange,
            placeHolder = "Cidade"
        )
    }
}

@Composable
fun FieldTextClient(
    modifier: Modifier,
    text: String,
    onTextChange: (String) -> Unit,
    placeHolder: String
) {
    Column(
        modifier = modifier
    ) {
        OutlinedTextField(
            value = text,
            onValueChange = onTextChange,
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .background(
                    Color.White,
                    RoundedCornerShape(12.dp)
                ),
            placeholder = {
                Text(
                    text = placeHolder,
                    color = Color.LightGray,
                    style = TextStyle(
                        fontSize = 16.sp,
                        lineHeight = 60.sp
                    )
                )
            },
            shape = RoundedCornerShape(12.dp),
            singleLine = true,
            textStyle = TextStyle(
                color = Color.Black,
                fontSize = 16.sp,
                lineHeight = 60.sp
            )
        )
    }
}