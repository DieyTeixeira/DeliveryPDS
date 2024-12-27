package com.codek.deliverypds.app.repository

import android.graphics.Bitmap
import android.util.Log
import com.codek.deliverypds.ui.login.state.LoginState
import com.codek.deliverypds.ui.registers.address.state.AddressUiState
import com.codek.deliverypds.ui.registers.product.state.ProductUiState
import com.codek.deliverypds.ui.registers.user.state.UserUiState
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream

class AuthRepository(
    private val firebaseAuth: FirebaseAuth
) {

    // Login com email e senha (Sign In)
    suspend fun signIn(email: String, password: String): LoginState {
        return try {
            val authResult = firebaseAuth.signInWithEmailAndPassword(email, password).await()
            LoginState.Success(userId = authResult.user?.uid ?: "")
        } catch (e: FirebaseAuthException) {
            LoginState.Error(e.errorCode)
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

class FirestoreRepository(
    private val firebaseFirestore: FirebaseFirestore,
    private val firebaseAuth: FirebaseAuth
) {

    // Salvar registro no Firestore
    suspend fun salvarUser(user: UserUiState) {
        try {
            val userEmail = firebaseAuth.currentUser?.email
                ?: throw Exception("Usuário não está logado")
            val userMap = mapOf(
                "nome" to user.name,
                "cpf" to user.cpf,
                "telefone" to user.phone
            )
            firebaseFirestore.collection(userEmail)
                .document("User")
                .set(userMap)
                .await()

            Log.d("FirestoreRepository", "Registro salvo com sucesso na coleção $userEmail")
        } catch (e: Exception) {
            Log.e("FirestoreRepository", "Erro ao salvar registro", e)
        }
    }

    // Encontrar registro no Firestore
    suspend fun findUser(): UserUiState? {
        return try {
            val userEmail = firebaseAuth.currentUser?.email
                ?: throw Exception("Usuário não está logado")
            val documentSnapshot = firebaseFirestore.collection(userEmail)
                .document("User")
                .get()
                .await()
            if (documentSnapshot.exists()) {
                val userData = documentSnapshot.data
                userData?.let {
                    UserUiState(
                        name = it["nome"] as? String ?: "",
                        cpf = it["cpf"] as? String ?: "",
                        phone = it["telefone"] as? String ?: ""
                    )
                } ?: UserUiState()
            } else {
                null
            }
        } catch (e: Exception) {
            throw e
        }
    }

    // Salvar registro no Firestore
    suspend fun salvarAddress(address: AddressUiState) {
        try {
            val userEmail = firebaseAuth.currentUser?.email
                ?: throw Exception("Usuário não está logado")
            val addressId = firebaseFirestore.collection(userEmail)
                .document("Addresses")
                .collection("subcollection")
                .document()
                .id
            val addressMap = mapOf(
                "rua" to address.street,
                "número" to address.number,
                "complemento" to address.complement,
                "cep" to address.cep,
                "bairro" to address.district,
                "cidade" to address.city
            )
            firebaseFirestore.collection(userEmail)
                .document("Address")
                .collection("SubAddresses")
                .document(addressId)
                .set(addressMap)
                .await()

            Log.d("FirestoreRepository", "Registro salvo com sucesso na coleção $userEmail")
        } catch (e: Exception) {
            Log.e("FirestoreRepository", "Erro ao salvar registro", e)
        }
    }

    // Salvar registro no Firestore
    suspend fun salvarProduct(product: ProductUiState) {
        try {
            val userEmail = firebaseAuth.currentUser?.email
                ?: throw Exception("Usuário não está logado")
            val productId = firebaseFirestore.collection(userEmail)
                .document("Product")
                .collection("subcollection")
                .document()
                .id
            val productMap = mapOf(
                "nome" to product.name,
                "categoria" to product.category,
                "valor" to product.value,
                "link" to product.link
            )
            firebaseFirestore.collection(userEmail)
                .document("Product")
                .collection("SubProducts")
                .document(productId)
                .set(productMap)
                .await()

            Log.d("FirestoreRepository", "Registro salvo com sucesso na coleção $userEmail")
        } catch (e: Exception) {
            Log.e("FirestoreRepository", "Erro ao salvar registro", e)
        }
    }

    // Função para listar todos os produtos cadastrados no Firestore
    suspend fun listarProdutos(): List<ProductUiState> {
        return try {
            val userEmail = firebaseAuth.currentUser?.email
                ?: throw Exception("Usuário não está logado")

            // Busca todos os documentos na coleção "SubProducts" do usuário
            val querySnapshot = firebaseFirestore.collection(userEmail)
                .document("Product")
                .collection("SubProducts")
                .get()
                .await()

            // Converte os documentos em uma lista de objetos ProductUiState
            val productList = querySnapshot.documents.mapNotNull { documentSnapshot ->
                val data = documentSnapshot.data
                data?.let {
                    ProductUiState(
                        name = it["nome"] as? String ?: "",
                        category = it["categoria"] as? String ?: "",
                        value = it["valor"] as? String ?: "",
                        link = it["link"] as? String ?: ""
                    )
                }
            }

            Log.d("FirestoreRepository", "Produtos listados com sucesso")
            productList
        } catch (e: Exception) {
            Log.e("FirestoreRepository", "Erro ao listar produtos", e)
            emptyList()  // Retorna uma lista vazia em caso de erro
        }
    }
}

class StorageRepository(
    private val firebaseStorage: FirebaseStorage
) {

    // Função para fazer o upload de uma imagem
    suspend fun uploadPhoto(bitmap: Bitmap, fileName: String): String? {
        return withContext(Dispatchers.IO) {
            try {
                // Referência para o Firebase Storage
                val storageRef: StorageReference = firebaseStorage.reference

                // Referência para o arquivo que será armazenado no Storage
                val photoRef: StorageReference = storageRef.child("/$fileName.jpg")

                // Convertendo o Bitmap para um array de bytes
                val baos = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
                val data = baos.toByteArray()

                // Fazendo o upload do arquivo
                val uploadTask = photoRef.putBytes(data)

                // Aguarde o upload ser completado e obtenha a URL do arquivo
                val urlTask = uploadTask.continueWithTask { task ->
                    if (!task.isSuccessful) {
                        throw task.exception ?: Exception("Erro ao fazer upload")
                    }
                    // Retorna o URL de download do arquivo
                    photoRef.downloadUrl
                }

                // Retorna o link de download
                urlTask.await().toString()

            } catch (e: Exception) {
                Log.e("FirebaseStorageRepository", "Erro ao fazer upload", e)
                null
            }
        }
    }
}