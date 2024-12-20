package com.codek.deliverypds.app.repository

import android.graphics.Bitmap
import com.dropbox.core.DbxRequestConfig
import com.dropbox.core.v2.DbxClientV2
import com.dropbox.core.v2.files.FileMetadata
import com.dropbox.core.v2.files.WriteMode
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream

class DropboxRepository(private val accessToken: String) {

    suspend fun uploadPhoto(bitmap: Bitmap, fileName: String): String? {
        return withContext(Dispatchers.IO) {
            try {
                val dbxClient = getDropboxClient()
                val dropboxPath = "/cat_$fileName.jpg"

                // Converte o Bitmap para InputStream
                val outputStream = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
                val inputStream = ByteArrayInputStream(outputStream.toByteArray())

                // Faz o upload do arquivo para o Dropbox
                val metadata: FileMetadata = dbxClient.files()
                    .uploadBuilder(dropboxPath)
                    .withMode(WriteMode.OVERWRITE)
                    .uploadAndFinish(inputStream)

                // Gerar o link de compartilhamento
                val sharedLink = dbxClient.sharing().createSharedLinkWithSettings(dropboxPath).url
                sharedLink
            } catch (e: Exception) {
                null
            }
        }
    }

    private fun getDropboxClient(): DbxClientV2 {
        val config = DbxRequestConfig.newBuilder("deliverypds/android").build()
        return DbxClientV2(config, accessToken)
    }
}