package bob.colbaskin.webantpractice.add_photo.presentation.upload_photo

import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import bob.colbaskin.webantpractice.R
import bob.colbaskin.webantpractice.common.UiState
import bob.colbaskin.webantpractice.common.photo_api.domain.PhotosRepository
import bob.colbaskin.webantpractice.common.toUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UploadPhotoViewModel @Inject constructor(
    private val photosRepository: PhotosRepository,
    @ApplicationContext private val context: Context
): ViewModel(){
    var state by mutableStateOf(UploadPhotoState())
        private set

    fun onAction(action: UploadPhotoAction){
        when(action) {
            is UploadPhotoAction.SelectFileFromGallery -> setSelectedImage(action.uri)
            UploadPhotoAction.UploadFile -> uploadFile()
            else -> Unit
        }
    }

    private fun setSelectedImage(uri: Uri?) {
        state = state.copy(selectedImage = uri)
    }
    
    private fun uploadFile() {
        state = state.copy(file = UiState.Loading)
        viewModelScope.launch {
            try {
                val uri = state.selectedImage ?: throw Exception(context.getString(
                    R.string.empty_file_upload_error_title
                ))
                val (originalName, byteArray) = getFileDataFromUri(uri)
                val response = photosRepository.uploadFile(originalName, byteArray).toUiState()
                state = state.copy(file = response)
            } catch (e: Exception) {
                state = state.copy(file = UiState.Error(
                    title = context.getString(R.string.file_upload_error_title),
                    text = e.message.toString()
                ))
            }
        }
    }

    private fun getFileDataFromUri(uri: Uri): Pair<String, ByteArray> {
        val originalName = getFileName(uri)
        val inputStream = context.contentResolver.openInputStream(uri)!!
        val byteArray = inputStream.readBytes()
        inputStream.close()
        return originalName to byteArray
    }

    private fun getFileName(uri: Uri): String {
        context.contentResolver.query(
            uri,
            null,
            null,
            null,
            null
        )?.use { cursor ->
            val nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
            if (cursor.moveToFirst() && nameIndex != -1) return cursor.getString(nameIndex)
        }
        return "image_${System.currentTimeMillis()}"
    }
}
