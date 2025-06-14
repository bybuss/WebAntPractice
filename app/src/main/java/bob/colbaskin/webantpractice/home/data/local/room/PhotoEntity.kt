package bob.colbaskin.webantpractice.home.data.local.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "photos")
data class PhotoEntity(
    @PrimaryKey val id: Int,
    val fileId: Int,
    val filePath: String,
    val new: Boolean?,
    val popular: Boolean?,
    val localImagePath: String? = null,
    val lastUpdated: Long = System.currentTimeMillis(),
    var imageState: Int = 0
)
