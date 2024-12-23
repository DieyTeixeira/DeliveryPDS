package com.codek.deliverypds.ui.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.codek.deliverypds.R
import com.codek.deliverypds.app.theme.ColorSec
import com.codek.deliverypds.app.theme.Dark

@Composable
fun HomeHeader(
    searchText: String,
    onSearchTextChanged: (String) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp)
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(70.dp)
                .background(
                    Dark,
                    RoundedCornerShape(0.dp, 0.dp, 0.dp, 40.dp)
                )
                .padding(horizontal = 15.dp, vertical = 11.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.End
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(modifier = Modifier.size(65.dp))
                Spacer(modifier = Modifier.width(10.dp))
                // Campo de pesquisa funcional
                OutlinedTextField(
                    value = searchText,
                    onValueChange = onSearchTextChanged,
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(6f)
                        .background(
                            Color.White,
                            RoundedCornerShape(100)
                        ),
                    placeholder = {
                        Text(
                            text = "Pesquisar...",
                            color = Color.LightGray,
                            style = TextStyle(
                                fontSize = 16.sp,
                                lineHeight = 60.sp
                            )
                        )
                    },
                    trailingIcon = {
                        Image(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Ícone de pesquisa",
                            colorFilter = ColorFilter.tint(Color.DarkGray)
                        )
                    },
                    shape = RoundedCornerShape(100),
                    singleLine = true,
                    textStyle = TextStyle(
                        color = Color.Black,
                        fontSize = 16.sp,
                        lineHeight = 60.sp
                    )
                )
            }
        }

        Column(
            modifier = Modifier
                .width(80.dp)
                .height(70.dp)
                .background(
                    Color.White,
                    RoundedCornerShape(0.dp, 35.dp, 0.dp, 35.dp)
                ),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo_pds),
                contentDescription = "Logo",
                modifier = Modifier
                    .size(65.dp)
                    .clip(RoundedCornerShape(100))
            )
        }
    }
}

@Preview
@Composable
private fun PreviewHomeHeader() {
    HomeHeader(
        searchText = "",
        onSearchTextChanged = {}
    )
}