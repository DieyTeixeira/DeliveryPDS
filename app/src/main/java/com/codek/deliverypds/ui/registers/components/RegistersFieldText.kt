package com.codek.deliverypds.ui.registers.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun UserFieldText(
    text: String,
    onTextChange: (String) -> Unit,
    placeHolder: String
) {
    Column(
        modifier = Modifier
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
            ),
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.LightGray,
                unfocusedIndicatorColor = Color.Transparent,
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White
            )
        )
    }
}