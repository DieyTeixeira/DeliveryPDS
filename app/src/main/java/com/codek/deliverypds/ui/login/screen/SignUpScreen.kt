package com.codek.deliverypds.ui.login.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.codek.deliverypds.ui.login.components.TextWithIcon
import com.codek.deliverypds.ui.login.components.containsDigit
import com.codek.deliverypds.ui.login.components.containsLowerCase
import com.codek.deliverypds.ui.login.components.containsSpecialCharacter
import com.codek.deliverypds.ui.login.components.containsUpperCase
import com.codek.deliverypds.app.theme.ColorError
import com.codek.deliverypds.app.theme.ColorPri
import com.codek.deliverypds.app.theme.ColorSucess
import com.codek.deliverypds.ui.login.viewmodel.LoginViewModel

@Composable
fun SignUpScreen(
    viewModel: LoginViewModel,
    onSignUpClick: () -> Unit,
    onSignInClick: () -> Unit
) {

    val focusManager = LocalFocusManager.current
    val loginUiState by viewModel.loginUiState.collectAsState()
    var passwordVisible by remember { mutableStateOf(false) }
    var confPasswordVisible by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                interactionSource = null,
                indication = null
            ) {
                focusManager.clearFocus()
            }
    ) {

        // Espaçamento para a barra de notificações
        Box(modifier = Modifier.fillMaxWidth().height(40.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(30.dp),
            verticalArrangement = Arrangement.spacedBy(15.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Cadastro de usuário",
                color = Color.Gray,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
            OutlinedTextField(
                value = loginUiState.email,
                onValueChange = { viewModel.onEmailChange(it) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .background(
                        Color.White,
                        RoundedCornerShape(30)
                    ),
                placeholder = {
                    Text(
                        text = "Email",
                        color = Color.Gray,
                        style = TextStyle(
                            fontSize = 16.sp,
                            lineHeight = 60.sp
                        )
                    )
                },
                leadingIcon = {
                    Image(
                        imageVector = Icons.Default.Email,
                        contentDescription = "Ícone de email",
                        colorFilter = ColorFilter.tint(Color.DarkGray)
                    )
                },
                shape = RoundedCornerShape(30),
                singleLine = true,
                textStyle = TextStyle(
                    color = Color.Black,
                    fontSize = 16.sp,
                    lineHeight = 60.sp
                ),
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.LightGray,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedContainerColor = Color.LightGray.copy(alpha = 0.5f),
                    unfocusedContainerColor = Color.LightGray.copy(alpha = 0.5f),
                    disabledIndicatorColor = Color.Gray,
                    errorIndicatorColor = Color.Red
                )
            )
            OutlinedTextField(
                value = loginUiState.password,
                onValueChange = { viewModel.onPasswordChange(it) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .background(
                        Color.White,
                        RoundedCornerShape(30)
                    ),
                placeholder = {
                    Text(
                        text = "Senha",
                        color = Color.Gray,
                        style = TextStyle(
                            fontSize = 16.sp,
                            lineHeight = 60.sp
                        )
                    )
                },
                leadingIcon = {
                    Image(
                        imageVector = Icons.Default.Lock,
                        contentDescription = "Ícone de senha",
                        colorFilter = ColorFilter.tint(Color.DarkGray)
                    )
                },
                trailingIcon = {
                    val image = if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                    val description = if (passwordVisible) "Ocultar senha" else "Mostrar senha"

                    Image(
                        imageVector = image,
                        contentDescription = description,
                        colorFilter = ColorFilter.tint(Color.DarkGray),
                        modifier = Modifier.clickable { passwordVisible = !passwordVisible }
                    )
                },
                shape = RoundedCornerShape(30),
                singleLine = true,
                textStyle = TextStyle(
                    color = Color.Black,
                    fontSize = 16.sp,
                    lineHeight = 60.sp
                ),
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.LightGray,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedContainerColor = Color.LightGray.copy(alpha = 0.5f),
                    unfocusedContainerColor = Color.LightGray.copy(alpha = 0.5f),
                    disabledIndicatorColor = Color.Gray,
                    errorIndicatorColor = Color.Red
                )
            )
            OutlinedTextField(
                value = loginUiState.confirmPassword,
                onValueChange = { viewModel.onConfirmPasswordChange(it) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .background(
                        Color.White,
                        RoundedCornerShape(30)
                    ),
                placeholder = {
                    Text(
                        text = "Senha",
                        color = Color.Gray,
                        style = TextStyle(
                            fontSize = 16.sp,
                            lineHeight = 60.sp
                        )
                    )
                },
                leadingIcon = {
                    Image(
                        imageVector = Icons.Default.Lock,
                        contentDescription = "Ícone de senha",
                        colorFilter = ColorFilter.tint(Color.DarkGray)
                    )
                },
                trailingIcon = {
                    val image = if (confPasswordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                    val description = if (confPasswordVisible) "Ocultar senha" else "Mostrar senha"

                    Image(
                        imageVector = image,
                        contentDescription = description,
                        colorFilter = ColorFilter.tint(Color.DarkGray),
                        modifier = Modifier.clickable { confPasswordVisible = !confPasswordVisible }
                    )
                },
                shape = RoundedCornerShape(30),
                singleLine = true,
                textStyle = TextStyle(
                    color = Color.Black,
                    fontSize = 16.sp,
                    lineHeight = 60.sp
                ),
                visualTransformation = if (confPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.LightGray,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedContainerColor = Color.LightGray.copy(alpha = 0.5f),
                    unfocusedContainerColor = Color.LightGray.copy(alpha = 0.5f),
                    disabledIndicatorColor = Color.Gray,
                    errorIndicatorColor = Color.Red
                )
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(90.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth(0.8f),
                        horizontalAlignment = Alignment.Start
                    ) {
                        TextWithIcon(
                            textValue = "Sua senha deve conter no mínimo 8 caracteres",
                            iconName = if (loginUiState.password.length >= 8) Icons.Filled.CheckCircle else Icons.Filled.Cancel,
                            iconColor = if (loginUiState.password.length >= 8) ColorSucess else ColorError
                        )
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(0.8f)
                    ) {
                        Column(
                            horizontalAlignment = Alignment.Start
                        ) {
                            TextWithIcon(
                                textValue = "1 letra maiúscula",
                                iconName = if (containsUpperCase(loginUiState.password)) Icons.Filled.CheckCircle else Icons.Filled.Cancel,
                                iconColor = if (containsUpperCase(loginUiState.password)) ColorSucess else ColorError
                            )
                            TextWithIcon(
                                textValue = "1 letra minúscula",
                                iconName = if (containsLowerCase(loginUiState.password)) Icons.Filled.CheckCircle else Icons.Filled.Cancel,
                                iconColor = if (containsLowerCase(loginUiState.password)) ColorSucess else ColorError
                            )
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Column(
                            horizontalAlignment = Alignment.Start
                        ) {
                            TextWithIcon(
                                textValue = "1 número",
                                iconName = if (containsDigit(loginUiState.password)) Icons.Filled.CheckCircle else Icons.Filled.Cancel,
                                iconColor = if (containsDigit(loginUiState.password)) ColorSucess else ColorError
                            )
                            TextWithIcon(
                                textValue = "1 caractere especial",
                                iconName = if (containsSpecialCharacter(loginUiState.password)) Icons.Filled.CheckCircle else Icons.Filled.Cancel,
                                iconColor = if (containsSpecialCharacter(loginUiState.password)) ColorSucess else ColorError
                            )
                        }
                    }
                }
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .background(
                        ColorPri,
                        RoundedCornerShape(30)
                    )
                    .clickable(
                        interactionSource = null,
                        indication = null
                    ) {
                        onSignUpClick()
//                        viewModel.signUp()
                    }
            ) {
                Text(
                    text = "Cadastrar",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .border(
                        1.dp,
                        ColorPri,
                        RoundedCornerShape(30)
                    )
                    .clickable(
                        interactionSource = null,
                        indication = null
                    ) { onSignInClick() }
            ) {
                Text(
                    text = "Login",
                    color = ColorPri,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }
}