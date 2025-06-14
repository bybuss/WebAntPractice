package bob.colbaskin.webantpractice.home.data.local.room

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface PhotoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPhotos(photos: List<PhotoEntity>)

    @Query("SELECT * FROM photos WHERE new = :isNew AND popular = :isPopular ORDER BY id DESC")
    fun getPhotos(isNew: Boolean?, isPopular: Boolean?): PagingSource<Int, PhotoEntity>

    @Query("SELECT * FROM photos WHERE filePath = :path LIMIT 1")
    suspend fun getByPath(path: String): PhotoEntity?

    @Query("SELECT * FROM photos WHERE new = :isNew AND popular = :isPopular")
    suspend fun getAllByType(isNew: Boolean?, isPopular: Boolean?): List<PhotoEntity>

    @Query("DELETE FROM photos WHERE new = :isNew AND popular = :isPopular")
    suspend fun clearByType(isNew: Boolean?, isPopular: Boolean?)

    @Update
    suspend fun updatePhoto(photo: PhotoEntity)
}
