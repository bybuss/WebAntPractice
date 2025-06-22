package bob.colbaskin.webantpractice.auth.data.models

import kotlinx.serialization.Serializable

@Serializable
data class ChangePasswordBody(
    val oldPassword: String,
    val plainPassword: String
)
