package bob.colbaskin.webantpractice.profile.domain.models

data class User(
    val displayName: String,
    val birthday: Long,
    val email: String,
    val phone: String
)