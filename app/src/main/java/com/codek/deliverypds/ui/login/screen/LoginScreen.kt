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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
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
import com.codek.deliverypds.app.theme.ColorPri
import com.codek.deliverypds.ui.login.viewmodel.LoginViewModel
import androidx.compose.ui.platform.LocalConfiguration
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

    val screenHeight = LocalConfiguration.current.screenHeightDp
    val halfScreenHeight = ((screenHeight / 2) + 40).dp
    val medScreenHeight = halfScreenHeight + 100.dp
    val plusScreenHeight = halfScreenHeight + 130.dp
    val roundedShape = 40.dp
    val animateDuration = 500

    val loginState by viewModel.loginState.collectAsState()
    var errorMessage by remember { mutableStateOf("") }
    var showMessage by remember { mutableStateOf(false) }

    var selected by remember { mutableStateOf(false) }

    val setLogo by animateDpAsState(if (selected) medScreenHeight else 0.dp, tween(animateDuration))
    val scaleLogo by animateFloatAsState(if (selected) 0.6f else 1f, tween(animateDuration))

    val setInfo by animateDpAsState(if (selected) 260.dp else 0.dp, tween(animateDuration))

    val setScreen by animateDpAsState(if (selected) 0.dp else halfScreenHeight, tween(animateDuration))
    val sizeScreen by animateDpAsState(if (selected) plusScreenHeight else halfScreenHeight, tween(animateDuration))

    val roundedShapeUp by animateDpAsState(if (selected) roundedShape else 0.dp, tween(animateDuration))
    val roundedShapeDown by animateDpAsState(if (selected) 0.dp else roundedShape, tween(animateDuration))

    var messageKey by remember { mutableStateOf(0) }
    var closeInfo by remember { mutableStateOf(0) }

    LaunchedEffect(loginState) {
        if (loginState is LoginState.Success) {
            onLoginSuccess()
        }
        if (loginState is LoginState.Error) {
            errorMessage = (loginState as LoginState.Error).message
            showMessage = true
        }
    }

    Box (
        modifier = Modifier
            .fillMaxSize()
            .background(ColorPri)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(20.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = errorMessage,
                    color = Color.Black
                )
            }
            Spacer(modifier = Modifier.height(20.dp))
            Image(
                painter = painterResource(id = R.drawable.logo_panelinha),
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
                if (showMessage) {
                    messageKey++
                    MensagemError(errorMessage, messageKey, closeInfo, selected)
                }
                when (loginState) {
                    is LoginState.Loading -> CircleLoading(color = Color.White)
                    is LoginState.Success -> MensagemSuccess(messageKey)
//                    is LoginState.Error -> MensagemError(errorMessage, messageKey, closeInfo, selected)
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
                                roundedShapeDown,
                                roundedShapeDown,
                                roundedShapeUp,
                                roundedShapeUp
                            )
                        )
                )
                this@Column.AnimatedVisibility(
                    visible = !selected,
                    enter = fadeIn(animationSpec = tween(animateDuration + 200)),
                    exit = fadeOut(animationSpec = tween(animateDuration - 100))
                ) {
                    SignInScreen(
                        viewModel = viewModel,
                        sizeScreen = halfScreenHeight,
                        onSignInClick = {
                            messageKey++
                            if (loginState != LoginState.Loading) {
                                onSignInClick()
                            }
                        },
                        onSignUpClick = {
                            selected = !selected
                            closeInfo++
                        }
                    )
                }
                this@Column.AnimatedVisibility(
                    visible = selected,
                    enter = fadeIn(animationSpec = tween(animateDuration + 200)),
                    exit = fadeOut(animationSpec = tween(animateDuration - 100))
                ) {
                    SignUpScreen(
                        viewModel = viewModel,
                        onSignUpClick = {
                            messageKey++
                            onSignUpClick()
                        },
                        onSignInClick = {
                            selected = !selected
                            closeInfo++
                        }
                    )
                }
            }
        }
    }
}