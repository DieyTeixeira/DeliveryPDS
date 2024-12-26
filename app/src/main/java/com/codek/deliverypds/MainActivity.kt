package com.codek.deliverypds

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.codek.deliverypds.app.theme.DeliveryPDSTheme
import com.codek.deliverypds.ui.cart.navigation.cartScreen
import com.codek.deliverypds.ui.cart.viewmodel.CartViewModel
import com.codek.deliverypds.ui.config.navigation.configScreen
import com.codek.deliverypds.ui.config.viewmodel.RegistersViewModel
import com.codek.deliverypds.ui.home.navigation.homeScreen
import com.codek.deliverypds.ui.home.viewmodel.HomeViewModel
import com.codek.deliverypds.ui.login.navigation.loginScreen
import com.codek.deliverypds.ui.login.viewmodel.LoginViewModel
import com.codek.deliverypds.ui.payment.navigation.paymentScreen
import com.codek.deliverypds.ui.registers.address.navigation.addressScreen
import com.codek.deliverypds.ui.registers.category.navigation.categoryScreen
import com.codek.deliverypds.ui.registers.product.navigation.productScreen
import com.codek.deliverypds.ui.registers.user.navigation.userScreen
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
    object Payment : Screen("payment")
    object User : Screen("user")
    object Address : Screen("address")
    object Category : Screen("category")
    object Product : Screen("product")
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
        val registersViewModel: RegistersViewModel by viewModel()

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
                        onNavigateToConfig = { navController.navigateToScreen("config", "home") },
                        onSignOut = { navController.navigateToScreen("login", "home") },
                        onNavigateToCart = { navController.navigateToScreen("cart", "home") }
                    )
                    cartScreen(
                        cartViewModel = cartViewModel,
                        onNavigateToHome = { navController.navigateToScreen("home", "cart") },
                        onNavigateToConfig = { navController.navigateToScreen("config", "cart") },
                        onSignOut = { navController.navigateToScreen("login", "cart") },
                        onNavigateToPayment = { navController.navigateToScreen("payment", "cart") }
                    )
                    paymentScreen(
                        cartViewModel = cartViewModel,
                        onNavigateToHome = { navController.navigateToScreen("home", "payment") }
                    )
                    configScreen(
                        onNavigateToHome = { navController.navigateToScreen("home", "config") },
                        onSignOut = { navController.navigateToScreen("login", "config") },
                        onNavigateToUser = { navController.navigateToScreen("user", "config") },
                        onNavigateToAddress = { navController.navigateToScreen("address", "config") },
                        onNavigateToCategory = { navController.navigateToScreen("category", "config") },
                        onNavigateToProduct = { navController.navigateToScreen("product", "config") }
                    )
                    userScreen(
                        onNavigateToHome = { navController.navigateToScreen("home", "user") },
                        onNavigateToConfig = { navController.navigateToScreen("config", "user") },
                        onSignOut = { navController.navigateToScreen("login", "user") }
                    )
                    productScreen(
                        registersViewModel = registersViewModel,
                        onNavigateToHome = { navController.navigateToScreen("home", "product") },
                        onNavigateToConfig = { navController.navigateToScreen("config", "product") },
                        onSignOut = { navController.navigateToScreen("login", "product") }
                    )
                    categoryScreen(
                        registersViewModel = registersViewModel,
                        onNavigateToHome = { navController.navigateToScreen("home", "category") },
                        onNavigateToConfig = { navController.navigateToScreen("config", "category") },
                        onSignOut = { navController.navigateToScreen("login", "category") }
                    )
                    addressScreen(
                        onNavigateToHome = { navController.navigateToScreen("home", "address") },
                        onNavigateToConfig = { navController.navigateToScreen("config", "address") },
                        onSignOut = { navController.navigateToScreen("login", "address") }
                    )
                }
            }
        }
    }
}