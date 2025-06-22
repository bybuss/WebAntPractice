package bob.colbaskin.webantpractice.auth.data.models

import bob.colbaskin.webantpractice.common.user_prefs.domain.models.User
import bob.colbaskin.webantpractice.common.utils.parseToMillis
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserResponse(
    val id: Int? = null,
    @SerialName("@id") val atId: String? = null,
    val birthday: String,
    val displayName: String,
    val email: String,
    val phone: String,
    val roles: List<String>? = null
)

fun UserResponse.toDomain(): User {
    val userId = this.id
        ?: this.atId?.substringAfterLast("/")?.toInt()
        ?: 0
    return User(
        id = userId,
        email = this.email,
        birthday = this.birthday.parseToMillis(),
        displayName = this.displayName,
        phone = this.phone
    )
}
