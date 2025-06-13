package bob.colbaskin.webantpractice.home.data

import bob.colbaskin.webantpractice.home.data.models.PhotoFileResponse
import bob.colbaskin.webantpractice.home.data.models.PhotoResponse
import bob.colbaskin.webantpractice.home.data.models.PhotosResponse
import bob.colbaskin.webantpractice.home.domain.models.Photo
import bob.colbaskin.webantpractice.home.domain.models.PhotoFile

fun PhotosResponse.toDomain(): List<Photo> {
    return this.hydraMember.map { it.toDomain()}
}

fun PhotoResponse.toDomain(): Photo {
    return Photo(
        id = this.id,
        file = this.file.toDomain(),
        new = this.new,
        popular = this.popular
    )
}

fun PhotoFileResponse.toDomain(): PhotoFile {
    return PhotoFile(
        id = this.id,
        path = this.path
    )
}
