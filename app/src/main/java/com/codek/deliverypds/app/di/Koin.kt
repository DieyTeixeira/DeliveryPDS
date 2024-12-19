package com.codek.deliverypds.app.di

import com.codek.deliverypds.app.repository.AuthRepository
import com.codek.deliverypds.ui.cart.viewmodel.CartViewModel
import com.codek.deliverypds.ui.home.viewmodel.HomeViewModel
import com.codek.deliverypds.ui.login.viewmodel.LoginViewModel
import com.google.firebase.auth.FirebaseAuth
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    single { AuthRepository(FirebaseAuth.getInstance()) }
    single { HomeViewModel() }
    single { CartViewModel() }
    single { LoginViewModel(get()) }

}