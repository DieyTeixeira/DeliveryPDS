package com.codek.deliverypds.ui.home.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModelProvider
import coil.Coil.imageLoader
import coil.ImageLoader
import coil.memory.MemoryCache
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.codek.deliverypds.ui.home.state.CategoryUrl
import com.codek.deliverypds.ui.home.state.Product
import com.codek.deliverypds.ui.home.state.categoriesUrl
import com.codek.deliverypds.ui.home.state.products
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class HomeViewModel(private val context: Context) : ViewModel() {
    var searchText by mutableStateOf("")
    var selectedCategory by mutableStateOf(0)
    var selectedProduct by mutableStateOf<Product?>(null)

    val filteredProducts: List<Product>
        get() = products.filter {
            (selectedCategory == 0 || it.categoryId == selectedCategory) &&
                    it.name.contains(searchText, ignoreCase = true)
        }

    val category: List<CategoryUrl> = categoriesUrl

    suspend fun preloadImages() {
        val imageLoader = ImageLoader.Builder(context)
            .memoryCache { MemoryCache.Builder(context).maxSizePercent(0.25).build() }  // Use mais memória para cache
            .diskCachePolicy(CachePolicy.ENABLED) // Certifique-se de que o cache de disco está ativado
            .build()

        withContext(Dispatchers.IO) {
            categoriesUrl.forEach { category ->
                val request = ImageRequest.Builder(context)
                    .data(category.imageRes)
                    .allowHardware(false)  // Certifique-se de não usar o cache de hardware
                    .memoryCacheKey(category.imageRes.toString()) // Use um cache de memória
                    .build()
                try {
                    imageLoader.execute(request)
                    Log.d("CoilPreload", "Imagem pré-carregada: ${category.imageRes}")
                } catch (e: Exception) {
                    Log.e("CoilPreload", "Erro ao pré-carregar imagem: ${category.imageRes}", e)
                }
            }
        }
    }
}