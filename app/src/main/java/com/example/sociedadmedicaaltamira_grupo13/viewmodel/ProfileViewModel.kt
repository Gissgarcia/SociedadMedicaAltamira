package com.example.sociedadmedicaaltamira_grupo13.viewmodel


import android.app.Application
import android.net.Uri
import androidx.core.content.FileProvider
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.io.File

data class ProfileState(
    val imageUri: Uri? = null,     // foto elegida o tomada
    val tempPhotoUri: Uri? = null, // URI temporal para TakePicture
    val message: String? = null
)

class ProfileViewModel(app: Application) : AndroidViewModel(app) {
    private val _state = MutableStateFlow(ProfileState())
    val state: StateFlow<ProfileState> = _state

    fun prepareCameraUri(): Uri {
        val cacheDir = File(getApplication<Application>().cacheDir, "images").apply { mkdirs() }
        val file = File.createTempFile("photo_", ".jpg", cacheDir)
        val uri = FileProvider.getUriForFile(
            getApplication(),
            "${getApplication<Application>().packageName}.fileprovider",
            file
        )
        _state.value = _state.value.copy(tempPhotoUri = uri)
        return uri
    }

    fun setFromGallery(uri: Uri?) {
        _state.value = _state.value.copy(imageUri = uri, message = if (uri != null) "Imagen desde galería" else null)
    }

    fun setFromCamera(ok: Boolean) {
        val s = _state.value
        _state.value = s.copy(imageUri = if (ok) s.tempPhotoUri else s.imageUri,
            message = if (ok) "Foto capturada" else null)
    }

    fun clearMessage() { _state.value = _state.value.copy(message = null) }
}
