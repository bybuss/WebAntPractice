package bob.colbaskin.webantpractice.home.data

import android.content.Context
import android.util.Log
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import java.io.IOException
import javax.inject.Inject

private const val TAG = "Photos"
private const val FILES_DIR_NAME = "images"

class ImageStorage @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val imagesDir by lazy {
        File(context.filesDir, FILES_DIR_NAME).apply {
            if (!exists()) {
                mkdirs()
            }
        }
    }

    fun saveImage(name: String, data: ByteArray): String {
        val file = File(imagesDir, "$name.jpg")
        try {
            file.outputStream().use { it.write(data) }
            return file.absolutePath
        } catch (e: IOException) {
            Log.e(TAG, "Error saving image: ${e.message}")
            throw e
        }
    }

    fun getImage(path: String): ByteArray? {
        return try {
            val file = File(path)
            if (file.exists()) {
                file.readBytes()
            } else {
                null
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error reading image: ${e.message}")
            null
        }
    }

    fun deleteImage(path: String) {
        try {
            File(path).delete()
        } catch (e: Exception) {
            Log.e(TAG, e.toString())
        }
    }
}
