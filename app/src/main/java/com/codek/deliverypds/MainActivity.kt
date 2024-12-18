package com.codek.deliverypds

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.codek.deliverypds.app.repository.AuthRepository
import com.codek.deliverypds.ui.login.screen.LoginScreen
import com.codek.deliverypds.app.theme.DeliveryPDSTheme
import com.codek.deliverypds.ui.home.navigation.homeScreen
import com.codek.deliverypds.ui.login.navigation.loginScreen
import com.codek.deliverypds.ui.login.viewmodel.LoginViewModel
import com.codek.deliverypds.ui.splash.navigation.splashScreen
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.delay

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Login : Screen("login")
    object Home : Screen("home")
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

        enableEdgeToEdge()
        setContent {
            DeliveryPDSTheme {
                val navController = rememberNavController()

                LaunchedEffect(Unit) {
                    delay(5000)
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
                        onLoginSuccess = { navController.navigateToScreen("home", "splash") }
                    )
                    homeScreen(
                        onSignOut = { navController.navigateToScreen("login", "home") }
                    )
                }
            }
        }
    }
}