package bob.colbaskin.webantpractice.auth.data.models

import kotlinx.serialization.Serializable

@Serializable
data class RegisterRequestBody(
    val email: String,
    val birthday: String,
    val displayName: String,
    val phone: String,
    val plainPassword: String
)
