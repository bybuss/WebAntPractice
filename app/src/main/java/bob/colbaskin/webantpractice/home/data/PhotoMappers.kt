package bob.colbaskin.webantpractice.home.data

import bob.colbaskin.webantpractice.common.utils.parseToMillis
import bob.colbaskin.webantpractice.home.data.models.FullFileResponse
import bob.colbaskin.webantpractice.home.data.models.FullPhotoResponse
import bob.colbaskin.webantpractice.home.data.models.PhotoFileResponse
import bob.colbaskin.webantpractice.home.data.models.PhotoResponse
import bob.colbaskin.webantpractice.home.data.models.PhotosResponse
import bob.colbaskin.webantpractice.home.data.models.UserResponse
import bob.colbaskin.webantpractice.home.domain.models.FullFile
import bob.colbaskin.webantpractice.home.domain.models.FullPhoto
import bob.colbaskin.webantpractice.home.domain.models.Photo
import bob.colbaskin.webantpractice.home.domain.models.PhotoFile
import bob.colbaskin.webantpractice.home.domain.models.User

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

fun FullPhotoResponse.toDomain(): FullPhoto {
    return FullPhoto(
        id = this.id,
        file = this.file.toDomain(),
        user = this.user.toDomain(),
        description = this.description,
        name = this.name,
        new = this.new,
        popular = this.popular,
        dateCreate = this.dateCreate.parseToMillis(),
        dateUpdate = this.dateUpdate.parseToMillis()
    )
}

fun FullFileResponse.toDomain(): FullFile {
    return FullFile(
        id = this.id,
        path = this.path,
        dateCreate = this.dateCreate.parseToMillis(),
        dateUpdate = this.dateUpdate.parseToMillis()
    )
}

fun UserResponse.toDomain(): User {
    return User(
        id = this.id,
        displayName = this.displayName,
        dateCreate = this.dateCreate.parseToMillis(),
        dateUpdate = this.dateUpdate.parseToMillis()
    )
}