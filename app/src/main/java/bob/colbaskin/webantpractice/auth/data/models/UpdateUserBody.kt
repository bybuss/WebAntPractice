package bob.colbaskin.webantpractice.auth.data.models

import kotlinx.serialization.Serializable

@Serializable
data class UpdateUserBody(
    val email: String,
    val birthday: String,
    val displayName: String,
    val phone: String
)
