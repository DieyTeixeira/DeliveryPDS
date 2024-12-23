package com.codek.deliverypds.app.barnavigation

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Output
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.codek.deliverypds.app.theme.ColorPri
import com.codek.deliverypds.app.theme.ColorSec
import kotlin.math.roundToInt

@OptIn(ExperimentalAnimationApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun BottomBarNavigation(
    onNavigateToHome: () -> Unit,
    onNavigateToConfig: () -> Unit,
    onSignOut: () -> Unit,
    onNavigateToCart: () -> Unit,
    cartItemCount: Int,
    content: @Composable () -> Unit
) {

    val bottomBarHeight = 85.dp
    val bottomBarHeightPx = with(LocalDensity.current) {
        bottomBarHeight.roundToPx().toFloat()
    }
    val bottomBarOffsetHeightPx = remember { mutableStateOf(0f) }
    val nestedScrollConnectionPx = remember {
        object : NestedScrollConnection {
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                val delta = available.y
                val newOffset = bottomBarOffsetHeightPx.value + delta
                bottomBarOffsetHeightPx.value = newOffset.coerceIn(-bottomBarHeightPx, 0f)
                return Offset.Zero
            }
        }
    }
    val scaffoldState = rememberScaffoldState()

    Scaffold(
        modifier = Modifier
            .background(
                Color.Transparent
            )
            .nestedScroll(nestedScrollConnectionPx),
        scaffoldState = scaffoldState,
        floatingActionButton = {
            FloatingActionButton(
                onClick = { onNavigateToCart() },
                backgroundColor = ColorSec,
                contentColor = Color.White,
                elevation = FloatingActionButtonDefaults.elevation(),
                modifier = Modifier
                    .offset {
                        IntOffset(x = 0, y = -bottomBarOffsetHeightPx.value.roundToInt())
                    }
            ) {
                Box(
                    modifier = Modifier
                        .size(43.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .align(Alignment.Center),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            Icons.Filled.ShoppingCart, ""
                        )
                    }
                    if (cartItemCount > 0) {
                        Column(
                            modifier = Modifier
                                .size(18.dp)
                                .background(
                                    Color.Red,
                                    RoundedCornerShape(100)
                                )
                                .align(Alignment.TopEnd),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = cartItemCount.toString(),
                                color = Color.White,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                lineHeight = 10.sp
                            )
                        }
                    }
                }
            }
        },
        isFloatingActionButtonDocked = true,
        floatingActionButtonPosition = FabPosition.End,
        bottomBar = {
            BottomAppBar(
                modifier = Modifier
                    .height(55.dp)
                    .offset {
                        IntOffset(x = 0, y = -bottomBarOffsetHeightPx.value.roundToInt())
                    },
                cutoutShape = RoundedCornerShape(50),
                backgroundColor = ColorSec,
                content = {
                    BottomNavigation(
                        backgroundColor = ColorPri,
                        contentColor = Color.White
                    ) {
                        Spacer(modifier = Modifier.width(10.dp))
                        IconButton(onClick = { onNavigateToHome() }) {
                            Icon(
                                Icons.Outlined.Home,
                                "",
                                tint = Color.White
                            )
                        }
                        IconButton(onClick = { onNavigateToConfig() }) {
                            Icon(
                                Icons.Outlined.Settings,
                                "",
                                tint = Color.White
                            )
                        }
                        IconButton(onClick = { onSignOut() }) {
                            Icon(
                                Icons.Outlined.Output,
                                "",
                                tint = Color.White
                            )
                        }
                        Spacer(modifier = Modifier.width(80.dp))
                    }
                }
            )
        },
        content = {
            Box(modifier = Modifier.fillMaxSize()) {
                content()
            }
        }
    )
}

@OptIn(ExperimentalAnimationApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun BarNavigation(
    onNavigateToHome: () -> Unit,
    onNavigateToConfig: () -> Unit,
    onSignOut: () -> Unit,
    content: @Composable () -> Unit
) {

    val context = LocalContext.current
    val bottomBarHeight = 85.dp
    val bottomBarHeightPx = with(LocalDensity.current) {
        bottomBarHeight.roundToPx().toFloat()
    }
    val bottomBarOffsetHeightPx = remember { mutableStateOf(0f) }
    val nestedScrollConnectionPx = remember {
        object : NestedScrollConnection {
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                val delta = available.y
                val newOffset = bottomBarOffsetHeightPx.value + delta
                bottomBarOffsetHeightPx.value = newOffset.coerceIn(-bottomBarHeightPx, 0f)
                return Offset.Zero
            }
        }
    }
    val scaffoldState = rememberScaffoldState()

    Scaffold(
        modifier = Modifier
            .background(
                Color.Transparent
            )
            .nestedScroll(nestedScrollConnectionPx),
        scaffoldState = scaffoldState,
        floatingActionButton = { },
        isFloatingActionButtonDocked = true,
        floatingActionButtonPosition = FabPosition.End,
        bottomBar = {
            BottomAppBar(
                modifier = Modifier
                    .height(55.dp)
                    .offset {
                        IntOffset(x = 0, y = -bottomBarOffsetHeightPx.value.roundToInt())
                    },
                cutoutShape = RoundedCornerShape(50),
                backgroundColor = ColorSec,
                content = {
                    BottomNavigation(
                        backgroundColor = ColorPri,
                        contentColor = Color.White
                    ) {
                        Spacer(modifier = Modifier.width(10.dp))
                        IconButton(onClick = { onNavigateToHome() }) {
                            Icon(
                                Icons.Outlined.Home,
                                "",
                                tint = Color.White
                            )
                        }
                        IconButton(onClick = { onNavigateToConfig() }) {
                            Icon(
                                Icons.Outlined.Settings,
                                "",
                                tint = Color.White
                            )
                        }
                        IconButton(onClick = { onSignOut() }) {
                            Icon(
                                Icons.Outlined.Output,
                                "",
                                tint = Color.White
                            )
                        }
                        Spacer(modifier = Modifier.width(80.dp))
                    }
                }
            )
        },
        content = {
            Box(modifier = Modifier.fillMaxSize()) {
                content()
            }
        }
    )
}

@Preview
@Composable
private fun PreviewBottomBarNavigation() {
    BottomBarNavigation(
        onNavigateToHome = {},
        onNavigateToConfig = {},
        onSignOut = {},
        onNavigateToCart = {},
        cartItemCount = 0,
        content = {}
    )
}