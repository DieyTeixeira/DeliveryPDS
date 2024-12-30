package com.codek.deliverypds.ui.config.viewmodel

import android.content.Context
import android.graphics.Bitmap
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codek.deliverypds.app.repository.StorageRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RegistersViewModel(
    private val storageRepository: StorageRepository
) : ViewModel() {

    fun uploadPhoto(context: Context, bitmap: Bitmap, fileName: String, onComplete: (String?) -> Unit) {
        viewModelScope.launch {
            val sharedLink = storageRepository.uploadPhoto(bitmap, fileName)
            withContext(Dispatchers.Main) {
                if (sharedLink != null) {
                    Toast.makeText(context, "Upload concluído com sucesso!", Toast.LENGTH_SHORT).show()
                    onComplete(sharedLink)
                } else {
                    Toast.makeText(context, "Erro ao fazer upload para o Dropbox", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}