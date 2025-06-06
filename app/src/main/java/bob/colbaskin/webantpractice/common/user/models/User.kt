package bob.colbaskin.webantpractice.common.user.models

data class User(
    val id: Int,
    val email: String,
    val userProfilePhoto: String,
    val birthday: Long,
    val displayName: String,
    val phone: String
)
