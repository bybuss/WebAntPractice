package bob.colbaskin.webantpractice.common.user_prefs.domain.models

import android.net.Uri


data class User(
    val id: Int,
    val email: String,
    val birthday: Long,
    val displayName: String,
    val userProfilePhoto: Uri? = null, // FIXME: REPLACE PAINTER TO IMAGE BITMAP FROM API
    val phone: String
)
