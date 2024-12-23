package com.codek.deliverypds.ui.config.components

import android.content.ContentResolver
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.codek.deliverypds.ui.config.viewmodel.ConfigViewModel
import java.io.InputStream

@Composable
fun ConfigPhotoPicker(
    configViewModel: ConfigViewModel,
    onNavigateToHome: () -> Unit
) {

    val clipboardManager = LocalClipboardManager.current
    val context = LocalContext.current
    var imageUri: Uri? by remember { mutableStateOf(null) }
    var bitmapImage: Bitmap? by remember { mutableStateOf(null) }
    var fileName by remember { mutableStateOf("") }
    var shareableLink by remember { mutableStateOf("") }

    val photoPicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri ->
        if (uri != null) {
            Log.d("PhotoPicker", "Selected URI: $uri")
            imageUri = uri
            bitmapImage = loadBitmapFromUri(uri, context)
        } else {
            Log.d("PhotoPicker", "No media selected")
        }
    }

    Box(
        modifier = Modifier
            .size(250.dp)
            .background(
                Color.LightGray,
                shape = RoundedCornerShape(15.dp)
            ),
        contentAlignment = Alignment.Center
    ) {
        AsyncImage(
            modifier = Modifier
                .size(250.dp)
                .clip(RoundedCornerShape(15.dp))
                .clickable {
                    photoPicker.launch(
                        PickVisualMediaRequest(
                            ActivityResultContracts.PickVisualMedia.ImageOnly
                        )
                    )
                },
            model = ImageRequest.Builder(LocalContext.current)
                .data(imageUri)
                .crossfade(enable = true)
                .build(),
            contentDescription = "Avatar Image",
            contentScale = ContentScale.Crop
        )
        Box(
            modifier = Modifier
                .size(60.dp)
                .align(Alignment.Center)
                .graphicsLayer {
                    alpha = 0.7f
                }
                .background(color = Color.Gray, shape = CircleShape)
                .clip(CircleShape)
                .clickable {
                    photoPicker.launch(
                        PickVisualMediaRequest(
                            ActivityResultContracts.PickVisualMedia.ImageOnly
                        )
                    )
                },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.CameraAlt,
                contentDescription = "Camera Icon",
                tint = Color.White,
                modifier = Modifier.size(30.dp)
            )
        }
    }
    Spacer(modifier = Modifier.height(24.dp))
    OutlinedTextField(
        value = fileName,
        onValueChange = { fileName = it },
        modifier = Modifier
            .fillMaxWidth(0.8f)
            .height(50.dp)
            .background(
                Color.White,
                RoundedCornerShape(30)
            ),
        placeholder = {
            Text(
                text = "Nome do Arquivo",
                color = Color.Gray,
                style = TextStyle(
                    fontSize = 16.sp,
                    lineHeight = 60.sp
                )
            )
        },
        leadingIcon = {
            Image(
                imageVector = Icons.Default.CameraAlt,
                contentDescription = "Ícone de imagem",
                colorFilter = ColorFilter.tint(Color.DarkGray)
            )
        },
        shape = RoundedCornerShape(30),
        singleLine = true,
        textStyle = TextStyle(
            color = Color.Black,
            fontSize = 16.sp,
            lineHeight = 60.sp
        ),
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Color.LightGray,
            unfocusedIndicatorColor = Color.Transparent,
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White,
            disabledIndicatorColor = Color.Gray,
            errorIndicatorColor = Color.Red
        )
    )
    Spacer(modifier = Modifier.height(24.dp))
    Button(onClick = {
        if (bitmapImage != null) {
            if (fileName.isBlank()) {
                Toast.makeText(context, "Insira o nome do arquivo!", Toast.LENGTH_SHORT).show()
            } else {
                configViewModel.uploadPhoto(context, bitmapImage!!, fileName) { link ->
                    if (link != null) {
                        shareableLink = link
                        clipboardManager.setText(AnnotatedString(shareableLink))
                        Toast.makeText(context, "Link de compartilhamento copiado!", Toast.LENGTH_SHORT).show()
                        onNavigateToHome()
                    } else {
                        Toast.makeText(context, "Erro ao obter o link", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        } else {
            Toast.makeText(context, "Selecione uma imagem primeiro", Toast.LENGTH_SHORT).show()
        }
    }) {
        Text(text = "Carregar Foto")
    }
}

fun loadBitmapFromUri(uri: Uri, context: Context): Bitmap? {
    val resolver: ContentResolver = context.contentResolver
    var inputStream: InputStream? = null
    try {
        inputStream = resolver.openInputStream(uri)
        return BitmapFactory.decodeStream(inputStream)
    } catch (e: Exception) {
        e.printStackTrace()
    } finally {
        inputStream?.close()
    }
    return null
}