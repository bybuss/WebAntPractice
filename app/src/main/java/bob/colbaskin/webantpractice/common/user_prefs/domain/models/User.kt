package bob.colbaskin.webantpractice.common.user_prefs.domain.models

data class User(
    val id: Int,
    val email: String,
    val birthday: Long,
    val displayName: String,
    val phone: String
)
