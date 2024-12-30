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
import com.codek.deliverypds.ui.registers.address.viewmodel.AddressViewModel
import com.codek.deliverypds.ui.registers.category.navigation.categoryScreenEdit
import com.codek.deliverypds.ui.registers.category.navigation.categoryScreenList
import com.codek.deliverypds.ui.registers.category.viewmodel.CategoryViewModel
import com.codek.deliverypds.ui.registers.product.navigation.productScreenEdit
import com.codek.deliverypds.ui.registers.product.navigation.productScreenList
import com.codek.deliverypds.ui.registers.product.viewmodel.ProductViewModel
import com.codek.deliverypds.ui.registers.user.navigation.userScreen
import com.codek.deliverypds.ui.registers.user.viewmodel.UserViewModel
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
    object CategoryEdit : Screen("categoryedit")
    object CategoryList : Screen("categorylist")
    object ProductEdit : Screen("productedit")
    object ProductList : Screen("productlist")
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
        val userViewModel: UserViewModel by viewModel()
        val addressViewModel: AddressViewModel by viewModel()
        val productViewModel: ProductViewModel by viewModel()
        val categoryViewModel: CategoryViewModel by viewModel()

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
                        onNavigateToCategoryEdit = { navController.navigateToScreen("categoryedit", "config") },
                        onNavigateToProductEdit = { navController.navigateToScreen("productedit", "config") },
                        onNavigateToCategoryList = { navController.navigateToScreen("categorylist", "config") },
                        onNavigateToProductList = { navController.navigateToScreen("productlist", "config") }
                    )
                    userScreen(
                        userViewModel = userViewModel,
                        onNavigateToHome = { navController.navigateToScreen("home", "user") },
                        onNavigateToConfig = { navController.navigateToScreen("config", "user") },
                        onSignOut = { navController.navigateToScreen("login", "user") }
                    )
                    productScreenEdit(
                        registersViewModel = registersViewModel,
                        productViewModel = productViewModel,
                        onNavigateToHome = { navController.navigateToScreen("home", "productedit") },
                        onNavigateToConfig = { navController.navigateToScreen("config", "productedit") },
                        onSignOut = { navController.navigateToScreen("login", "productedit") }
                    )
                    productScreenList(
                        productViewModel = productViewModel,
                        onNavigateToHome = { navController.navigateToScreen("home", "productlist") },
                        onNavigateToConfig = { navController.navigateToScreen("config", "productlist") },
                        onSignOut = { navController.navigateToScreen("login", "productlist") }
                    )
                    categoryScreenEdit(
                        registersViewModel = registersViewModel,
                        categoryViewModel = categoryViewModel,
                        onNavigateToHome = { navController.navigateToScreen("home", "categoryedit") },
                        onNavigateToConfig = { navController.navigateToScreen("config", "categoryedit") },
                        onSignOut = { navController.navigateToScreen("login", "categoryedit") }
                    )
                    categoryScreenList(
                        categoryViewModel = categoryViewModel,
                        onNavigateToHome = { navController.navigateToScreen("home", "categorylist") },
                        onNavigateToConfig = { navController.navigateToScreen("config", "categorylist") },
                        onSignOut = { navController.navigateToScreen("login", "categorylist") }
                    )
                    addressScreen(
                        addressViewModel = addressViewModel,
                        onNavigateToHome = { navController.navigateToScreen("home", "address") },
                        onNavigateToConfig = { navController.navigateToScreen("config", "address") },
                        onSignOut = { navController.navigateToScreen("login", "address") }
                    )
                }
            }
        }
    }
}