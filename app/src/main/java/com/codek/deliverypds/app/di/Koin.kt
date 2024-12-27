package com.codek.deliverypds.app.di

import com.codek.deliverypds.app.repository.AuthRepository
import com.codek.deliverypds.app.repository.DropboxRepository
import com.codek.deliverypds.app.repository.FirestoreRepository
import com.codek.deliverypds.app.repository.StorageRepository
import com.codek.deliverypds.ui.cart.viewmodel.CartViewModel
import com.codek.deliverypds.ui.config.viewmodel.RegistersViewModel
import com.codek.deliverypds.ui.home.viewmodel.HomeViewModel
import com.codek.deliverypds.ui.login.viewmodel.LoginViewModel
import com.codek.deliverypds.ui.registers.address.viewmodel.AddressViewModel
import com.codek.deliverypds.ui.registers.product.viewmodel.ProductViewModel
import com.codek.deliverypds.ui.registers.user.viewmodel.UserViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val dropboxToken = "sl.CDYthIWmYOKB7PTaVW7WhXlI6kvdFC6xT8wWGFZdyq0D00UGUgNSZxQq83mqwwpt3bzY7PvKjm6cncM81y4DOUVYdQuuZA8-YUkSkuDB86j__ACHDn_p_0N4tT_YbJBWMRzQHJj55e_6qiSbKw1lNsY"

val appModule = module {

    single { AuthRepository(FirebaseAuth.getInstance()) }
    single { FirestoreRepository(FirebaseFirestore.getInstance(), FirebaseAuth.getInstance()) }
    single { StorageRepository(FirebaseStorage.getInstance()) }
    single { DropboxRepository(dropboxToken) }

    viewModel { LoginViewModel(get()) } // firebase auth
    viewModel { HomeViewModel(get()) } // firebase auth
    viewModel { CartViewModel() }
    viewModel { RegistersViewModel(get()) } // firebase storage
    viewModel { UserViewModel(get()) } // firebase firestore
    viewModel { AddressViewModel(get()) } // firebase firestore
    viewModel { ProductViewModel(get()) } // firebase firestore

}