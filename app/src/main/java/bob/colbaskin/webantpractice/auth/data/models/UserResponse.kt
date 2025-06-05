package bob.colbaskin.webantpractice.auth.data.models

import bob.colbaskin.webantpractice.common.user_prefs.domain.models.User
import bob.colbaskin.webantpractice.common.utils.extractIdFromIri
import bob.colbaskin.webantpractice.common.utils.parseToMillis
import kotlinx.serialization.SerialName

data class UserResponse(
    @SerialName("@id") val id: String,
    val birthday: String,
    val displayName: String,
    val email: String,
    val phone: String,
    val roles: List<String>
)

fun UserResponse.toDomain(): User {
    return User(
        id = this.id.extractIdFromIri(),
        email = this.email,
        userProfilePhoto = null,
        birthday = this.birthday.parseToMillis(),
        displayName = this.displayName,
        phone = this.phone
    )
}
