package bob.colbaskin.webantpractice.profile.data.models

import bob.colbaskin.webantpractice.profile.domain.models.User
import kotlinx.serialization.Serializable

@Serializable
data class UserResponse(
    val displayName: String,
    val birthday: Long,
    val email: String,
    val phone: String
)

fun UserResponse.toDomain(): User {
    return User(
        displayName = this.displayName,
        birthday = this.birthday,
        email = this.email,
        phone = this.phone
    )
}
