package bob.colbaskin.webantpractice.profile.data

import android.content.Context
import android.util.Log
import bob.colbaskin.webantpractice.common.Result
import bob.colbaskin.webantpractice.common.utils.safeApiCall
import bob.colbaskin.webantpractice.profile.data.models.UserResponse
import bob.colbaskin.webantpractice.profile.data.models.toDomain
import bob.colbaskin.webantpractice.profile.domain.ProfileApiService
import bob.colbaskin.webantpractice.profile.domain.ProfileRepository
import bob.colbaskin.webantpractice.profile.domain.models.User
import javax.inject.Inject

private const val TAG = "Profile"

class ProfileRepositoryImpl @Inject constructor(
    private val context: Context,
    private val profileApi: ProfileApiService
): ProfileRepository {

    override suspend fun getUserById(id: Int): Result<User> {
        Log.d(TAG, "Getting user: $id from API...")
        return safeApiCall<UserResponse, User> (
            apiCall = { profileApi.getUserById(id) },
            successHandler = {  response ->
                val user = response.toDomain()
                Log.d(TAG, "User fetched successfully!\n $user")
                user
            },
            context = context
        )
    }
}
