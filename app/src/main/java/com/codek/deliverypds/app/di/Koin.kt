package com.codek.deliverypds.app.di

import com.codek.deliverypds.app.repository.AuthRepository
import com.codek.deliverypds.app.repository.DropboxRepository
import com.codek.deliverypds.ui.cart.viewmodel.CartViewModel
import com.codek.deliverypds.ui.home.viewmodel.HomeViewModel
import com.codek.deliverypds.ui.login.viewmodel.LoginViewModel
import com.codek.deliverypds.ui.config.viewmodel.ConfigViewModel
import com.google.firebase.auth.FirebaseAuth
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val dropboxToken = "sl.CDLIFvmLxQjUq-zhlEvXmq6ZiCOX3xC4gQ2iwpjBJYpF465sdBYgoZ5eiA24F0RXsnxg3mLYJy06RolAWqUgfUJ5x9bqINWIsG6Y8IL41Zx3nK7To6YzVQzp4ld0V2ofplpv_Sk6zwyQ0ZxRGSrlODk"

val appModule = module {

    single { AuthRepository(FirebaseAuth.getInstance()) }
    single { DropboxRepository(dropboxToken) }

    viewModel { LoginViewModel(get()) } // firebase auth
    viewModel { HomeViewModel(get()) } // firebase auth
    viewModel { CartViewModel() }
    viewModel { ConfigViewModel(get()) } // dropbox

}