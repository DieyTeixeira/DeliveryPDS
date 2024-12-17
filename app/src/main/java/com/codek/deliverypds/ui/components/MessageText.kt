package com.codek.deliverypds.ui.components

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.codek.deliverypds.ui.state.LoginState
import com.codek.deliverypds.ui.theme.ColorError
import com.codek.deliverypds.ui.theme.ColorSucess
import kotlinx.coroutines.delay

@Composable
fun MensagemErro(
    loginState: LoginState,
    messageKey: Int
) {
    var isErrorVisible by remember { mutableStateOf(false) }
    val errorMessage = (loginState as LoginState.Error).message

    LaunchedEffect(messageKey) {
        isErrorVisible = true
        delay(5000)
        isErrorVisible = false
    }

    AnimatedVisibility(
        visible = isErrorVisible,
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
                .background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(
                            ColorError.copy(alpha = 0.0f),
                            ColorError.copy(alpha = 0.7f),
                            ColorError, ColorError, ColorError,
                            ColorError.copy(alpha = 0.7f),
                            ColorError.copy(alpha = 0.0f)
                        )
                    )
                )
        ) {
            Text(
                text = errorMessage,
                color = Color.White,
                style = TextStyle.Default.copy(
                    fontSize = 14.sp,
                    fontStyle = FontStyle.Italic
                ),
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(5.dp)
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
        delay(5000)
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
                .background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(
                            ColorSucess.copy(alpha = 0.0f),
                            ColorSucess.copy(alpha = 0.7f),
                            ColorSucess, ColorSucess, ColorSucess,
                            ColorSucess.copy(alpha = 0.7f),
                            ColorSucess.copy(alpha = 0.0f)
                        )
                    )
                )
        ) {
            Text(
                text = successMessage,
                color = Color.White,
                style = TextStyle.Default.copy(
                    fontSize = 14.sp,
                    fontStyle = FontStyle.Italic
                ),
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(5.dp)
            )
        }
    }
}