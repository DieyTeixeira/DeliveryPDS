package com.codek.deliverypds.ui.login.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.codek.deliverypds.ui.login.state.LoginState
import com.codek.deliverypds.app.theme.ColorError
import com.codek.deliverypds.app.theme.ColorSucess
import com.codek.deliverypds.app.theme.DarkColorError
import com.codek.deliverypds.app.theme.DarkColorSucess
import kotlinx.coroutines.delay

@Composable
fun MensagemError(
    errorMessage: String,
    messageKey: Int,
    closeInfo: Int,
    selected: Boolean
) {
    var isErrorVisible by remember { mutableStateOf(false) }

    LaunchedEffect(messageKey) {
        isErrorVisible = true
        delay(2000)
        isErrorVisible = false
    }

    LaunchedEffect(closeInfo) {
        isErrorVisible = false
    }

    AnimatedVisibility(
        visible = isErrorVisible,
        enter = fadeIn(animationSpec = tween(durationMillis = 500)) +
                slideInVertically(
                    initialOffsetY = { if (selected) -260 else it },
                    animationSpec = tween(durationMillis = 500, easing = FastOutSlowInEasing)
                ),
        exit = fadeOut(animationSpec = tween(durationMillis = 300)) +
                slideOutVertically(
                    targetOffsetY = { if (selected) -260 else it },
                    animationSpec = tween(durationMillis = 300, easing = FastOutSlowInEasing)
                )
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
                .border(
                    width = 2.dp,
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            ColorError,
                            DarkColorError
                        )
                    ),
                    shape = RoundedCornerShape(15.dp)
                )
                .background(
                    color = ColorError,
                    shape = RoundedCornerShape(15.dp)
                )
        ) {
            Text(
                text = errorMessage,
                color = Color.White,
                style = TextStyle.Default.copy(
                    fontSize = 18.sp,
                    fontStyle = FontStyle.Italic
                ),
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(8.dp)
            )
        }
    }
}

@Composable
fun MensagemSuccess(
    messageKey: Int
) {
    var isSuccessVisible by remember { mutableStateOf(false) }
    val successMessage = "Login bem-sucedido!"

    LaunchedEffect(messageKey) {
        isSuccessVisible = true
        delay(3000)
        isSuccessVisible = false
    }

    AnimatedVisibility(
        visible = isSuccessVisible,
        enter = fadeIn(animationSpec = tween(durationMillis = 500)) +
                slideInVertically(
                    initialOffsetY = { it },
                    animationSpec = tween(durationMillis = 500, easing = FastOutSlowInEasing)
                ),
        exit = fadeOut(animationSpec = tween(durationMillis = 300)) +
                slideOutVertically(
                    targetOffsetY = { it },
                    animationSpec = tween(durationMillis = 300, easing = FastOutSlowInEasing)
                )
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
                .border(
                    width = 2.dp,
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            ColorSucess,
                            DarkColorSucess
                        )
                    ),
                    shape = RoundedCornerShape(15.dp)
                )
                .background(
                    color = ColorSucess,
                    shape = RoundedCornerShape(15.dp)
                )
        ) {
            Text(
                text = successMessage,
                color = Color.White,
                style = TextStyle.Default.copy(
                    fontSize = 18.sp,
                    fontStyle = FontStyle.Italic
                ),
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(8.dp)
            )
        }
    }
}

@Composable
fun ErrorBoxSignIn(messageError: String, offsetY: Dp) {
    Box(
        modifier = Modifier
            .width(300.dp)
            .offset(y = offsetY, x = 75.dp)
            .border(1.dp, ColorError, RoundedCornerShape(10.dp))
            .background(Color.White, RoundedCornerShape(10.dp))
            .padding(8.dp, 5.dp)
    ) {
        Text(
            text = messageError,
            color = ColorError,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}