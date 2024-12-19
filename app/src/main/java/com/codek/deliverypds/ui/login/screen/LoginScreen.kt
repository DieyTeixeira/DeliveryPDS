package com.codek.deliverypds.ui.login.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.codek.deliverypds.R
import com.codek.deliverypds.ui.login.state.LoginState
import com.codek.deliverypds.ui.login.viewmodel.LoginViewModel
import androidx.compose.ui.platform.LocalConfiguration
import com.codek.deliverypds.app.theme.ColorSec
import com.codek.deliverypds.app.theme.ManageStatusBarIcons
import com.codek.deliverypds.ui.login.components.CircleLoading
import com.codek.deliverypds.ui.login.components.MensagemError
import com.codek.deliverypds.ui.login.components.MensagemSuccess

@Composable
fun LoginScreen(
    viewModel: LoginViewModel,
    onSignInClick: () -> Unit,
    onSignUpClick: () -> Unit,
    onLoginSuccess: () -> Unit
) {
    // Cor dos ícones da status bar
    ManageStatusBarIcons(isIconBlack = true)

    val loginState by viewModel.loginState.collectAsState()
    var errorMessage by remember { mutableStateOf("") }
    var successMessage by remember { mutableStateOf("") }
    var changeScreen by remember { mutableStateOf(false) }
    var repeatMessage by remember { mutableStateOf(false) }

    /* ---------------------------- Configuração da animação da tela ---------------------------- */
    val screenHeight = LocalConfiguration.current.screenHeightDp
    val halfScreen = ((screenHeight / 2) + 40).dp
    val medScreen = halfScreen + 90.dp
    val plusScreen = halfScreen + 130.dp
    val roundedShape = 40.dp
    val animDuration = 500
    val setLogo by animateDpAsState(if (changeScreen) medScreen else 0.dp, tween(animDuration))
    val scaleLogo by animateFloatAsState(if (changeScreen) 0.6f else 1f, tween(animDuration))
    val setInfo by animateDpAsState(if (changeScreen) 240.dp else 0.dp, tween(animDuration))
    val setScreen by animateDpAsState(if (changeScreen) 0.dp else halfScreen, tween(animDuration))
    val sizeScreen by animateDpAsState(if (changeScreen) plusScreen else halfScreen, tween(animDuration))
    val shapeUp by animateDpAsState(if (changeScreen) roundedShape else 0.dp, tween(animDuration))
    val shapeDown by animateDpAsState(if (changeScreen) 0.dp else roundedShape, tween(animDuration))
    /* ------------------------------------------------------------------------------------------ */

    LaunchedEffect(loginState) {
        when (loginState) {
            is LoginState.Loading -> {
                errorMessage = ""
            }
            is LoginState.Success -> {
                successMessage = (loginState as LoginState.Success).userId
            }
            is LoginState.Error -> {
                errorMessage = (loginState as LoginState.Error).message
            }
            else -> {}
        }
    }

    Box (
        modifier = Modifier
            .fillMaxSize()
            .background(ColorSec)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(100.dp))
            Image(
                painter = painterResource(id = R.drawable.logo_pds),
                contentDescription = "Logo",
                modifier = Modifier
                    .size(200.dp)
                    .offset(y = setLogo)
                    .scale(scaleLogo)
                    .clip(RoundedCornerShape(100))
                    .clickable { viewModel.signOut() }
            )
            Spacer(modifier = Modifier.height(20.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp)
                    .offset(y = setInfo),
                contentAlignment = Alignment.Center
            ) {
                when (loginState) {
                    is LoginState.Loading -> CircleLoading(Color.White)
                    is LoginState.Success -> MensagemSuccess(successMessage, onLoginSuccess)
                    is LoginState.Error -> MensagemError(errorMessage, repeatMessage, changeScreen)
                    else -> {}
                }
            }
        }
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .offset(y = setScreen)
                        .height(sizeScreen)
                        .background(
                            color = Color.White,
                            shape = RoundedCornerShape(
                                shapeDown,
                                shapeDown,
                                shapeUp,
                                shapeUp
                            )
                        )
                )
                this@Column.AnimatedVisibility(
                    visible = !changeScreen,
                    enter = fadeIn(animationSpec = tween(animDuration + 200)),
                    exit = fadeOut(animationSpec = tween(animDuration - 100))
                ) {
                    SignInScreen(
                        viewModel = viewModel,
                        sizeScreen = halfScreen,
                        onSignInClick = {
                            repeatMessage = !repeatMessage
                            if (loginState != LoginState.Loading) {
                                onSignInClick()
                            }
                        },
                        onSignUpClick = {
                            changeScreen = !changeScreen
                        }
                    )
                }
                this@Column.AnimatedVisibility(
                    visible = changeScreen,
                    enter = fadeIn(animationSpec = tween(animDuration + 200)),
                    exit = fadeOut(animationSpec = tween(animDuration - 100))
                ) {
                    SignUpScreen(
                        viewModel = viewModel,
                        onSignUpClick = {
                            repeatMessage = !repeatMessage
                            onSignUpClick()
                        },
                        onSignInClick = {
                            changeScreen = !changeScreen
                        }
                    )
                }
            }
        }
    }
}