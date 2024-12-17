package com.codek.deliverypds.repository

import com.codek.deliverypds.ui.state.LoginState
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await

class AuthRepository(private val firebaseAuth: FirebaseAuth) {

    // Login com email e senha (Sign In)
    suspend fun signIn(email: String, password: String): LoginState {
        return try {
            val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()
            LoginState.Success(result.user?.uid ?: "")
        } catch (e: Exception) {
            LoginState.Error(e.message ?: "Erro desconhecido")
        }
    }

    // Criação de conta (Sign Up)
    suspend fun signUp(email: String, password: String): LoginState {
        return try {
            val result = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            LoginState.Success(result.user?.uid ?: "")
        } catch (e: Exception) {
            LoginState.Error(e.message ?: "Erro ao criar conta")
        }
    }

    // Logout (Sign Out)
    fun signOut() {
        firebaseAuth.signOut()
    }
}