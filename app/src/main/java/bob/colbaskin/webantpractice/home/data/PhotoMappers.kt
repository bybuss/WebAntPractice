package bob.colbaskin.webantpractice.home.data

import bob.colbaskin.webantpractice.home.data.local.room.PhotoEntity
import bob.colbaskin.webantpractice.home.domain.models.Photo
import bob.colbaskin.webantpractice.home.domain.models.PhotoFile

fun PhotoEntity.toDomain() = Photo(
    id = id,
    file = PhotoFile(fileId, filePath),
    new = new,
    popular = popular
)