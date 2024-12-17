package com.codek.deliverypds

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.viewmodel.compose.viewModel
import com.codek.deliverypds.repository.AuthRepository
import com.codek.deliverypds.ui.screen.LoginScreen
import com.codek.deliverypds.ui.state.LoginUiState
import com.codek.deliverypds.ui.theme.DeliveryPDSTheme
import com.codek.deliverypds.ui.viewmodel.LoginViewModel
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        FirebaseApp.initializeApp(this)

        enableEdgeToEdge()
        setContent {
            DeliveryPDSTheme {
                val authRepository = AuthRepository(FirebaseAuth.getInstance())
                val viewModel = LoginViewModel(authRepository)

                LoginScreen(viewModel)
            }
        }
    }
}