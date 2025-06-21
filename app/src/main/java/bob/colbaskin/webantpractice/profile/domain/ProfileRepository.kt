package bob.colbaskin.webantpractice.profile.domain

import bob.colbaskin.webantpractice.common.Result
import bob.colbaskin.webantpractice.profile.domain.models.User

interface ProfileRepository {

    suspend fun getUserById(id: Int): Result<User>
}