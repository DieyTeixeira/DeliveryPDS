package com.codek.deliverypds

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.codek.deliverypds.app.theme.DeliveryPDSTheme
import com.codek.deliverypds.ui.cart.navigation.cartScreen
import com.codek.deliverypds.ui.cart.viewmodel.CartViewModel
import com.codek.deliverypds.ui.home.navigation.homeScreen
import com.codek.deliverypds.ui.config.navigation.configScreen
import com.codek.deliverypds.ui.home.viewmodel.HomeViewModel
import com.codek.deliverypds.ui.login.navigation.loginScreen
import com.codek.deliverypds.ui.login.viewmodel.LoginViewModel
import com.codek.deliverypds.ui.config.viewmodel.ConfigViewModel
import com.codek.deliverypds.ui.splash.navigation.splashScreen
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.delay
import org.koin.androidx.viewmodel.ext.android.viewModel

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Login : Screen("login")
    object Home : Screen("home")
    object Cart : Screen("cart")
    object Config : Screen("config")
}

fun NavHostController.navigateToScreen(route: String, popUpToRoute: String? = null) {
    this.navigate(route) {
        popUpToRoute?.let {
            popUpTo(it) { inclusive = true }
        }
    }
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        FirebaseApp.initializeApp(this)

        val loginViewModel: LoginViewModel by viewModel()
        val cartViewModel: CartViewModel by viewModel()
        val homeViewModel: HomeViewModel by viewModel()
        val configViewModel: ConfigViewModel by viewModel()

        enableEdgeToEdge()
        setContent {
            DeliveryPDSTheme {

                val navController = rememberNavController()

                LaunchedEffect(Unit) {
                    homeViewModel.preloadImages()
                    delay(2000)
                    val currentUser = FirebaseAuth.getInstance().currentUser
                    if (currentUser != null) {
                        navController.navigateToScreen("home", "splash")
                    } else {
                        navController.navigateToScreen("login", "splash")
                    }
                }

                NavHost(
                    navController = navController,
                    startDestination = "splash"
                ) {
                    splashScreen()
                    loginScreen(
                        loginViewModel = loginViewModel,
                        onLoginSuccess = { navController.navigateToScreen("home", "splash") }
                    )
                    homeScreen(
                        homeViewModel = homeViewModel,
                        cartViewModel = cartViewModel,
                        onSignOut = { navController.navigateToScreen("login", "home") },
                        onNavigateToCart = { navController.navigateToScreen("cart", "home") },
                        onNavigateToConfig = { navController.navigateToScreen("config", "home") }
                    )
                    cartScreen(
                        cartViewModel = cartViewModel,
                        onNavigateToHome = { navController.navigateToScreen("home", "cart") }
                    )
                    configScreen(
                        configViewModel = configViewModel,
                        onNavigateToHome = { navController.navigateToScreen("home", "config") }
                    )
                }

                Box(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.BottomCenter
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(10.dp)
                            .background(Color.Yellow)
                    )
                }
            }
        }
    }
}