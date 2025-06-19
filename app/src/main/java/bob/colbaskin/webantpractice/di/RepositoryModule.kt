package bob.colbaskin.webantpractice.di

import android.content.Context
import bob.colbaskin.webantpractice.auth.data.AuthRepositoryImpl
import bob.colbaskin.webantpractice.auth.data.RefreshTokenRepositoryImpl
import bob.colbaskin.webantpractice.auth.domain.auth.AuthApiService
import bob.colbaskin.webantpractice.auth.domain.auth.AuthRepository
import bob.colbaskin.webantpractice.auth.domain.token.RefreshTokenRepository
import bob.colbaskin.webantpractice.auth.domain.token.RefreshTokenService
import bob.colbaskin.webantpractice.common.user_prefs.data.local.datastore.UserDataStore
import bob.colbaskin.webantpractice.common.user_prefs.domain.UserPreferencesRepository
import bob.colbaskin.webantpractice.common.user_prefs.data.UserPreferencesRepositoryImpl
import bob.colbaskin.webantpractice.di.token.TokenManager
import bob.colbaskin.webantpractice.common.photo_api.data.PhotosRepositoryImpl
import bob.colbaskin.webantpractice.common.photo_api.domain.PhotosApiService
import bob.colbaskin.webantpractice.common.photo_api.domain.PhotosRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideUserPreferencesRepository(dataStore: UserDataStore): UserPreferencesRepository {
        return UserPreferencesRepositoryImpl(dataStore)
    }

    @Provides
    @Singleton
    fun provideAuthApiService(retrofit: Retrofit): AuthApiService {
        return retrofit.create(AuthApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideAuthRepository(
        @ApplicationContext context: Context,
        authApi: AuthApiService,
        userPreferences: UserPreferencesRepository,
        tokenManager: TokenManager
    ): AuthRepository {
        return AuthRepositoryImpl(
            context = context,
            authApi = authApi,
            userPreferences = userPreferences,
            tokenManager = tokenManager
        )
    }

    @Provides
    @Singleton
    fun provideRefreshTokenService(retrofit: Retrofit): RefreshTokenService {
        return retrofit.create(RefreshTokenService::class.java)
    }

    @Provides
    @Singleton
    fun provideRefreshTokenRepository(
        @ApplicationContext context: Context,
        refreshTokenApi: RefreshTokenService,
        tokenManager: TokenManager
    ): RefreshTokenRepository {
        return RefreshTokenRepositoryImpl(
            context = context,
            refreshTokenApi = refreshTokenApi,
            tokenManager = tokenManager
        )
    }

    @Provides
    @Singleton
    fun providePhotosApiService(retrofit: Retrofit): PhotosApiService {
        return retrofit.create(PhotosApiService::class.java)
    }

    @Provides
    @Singleton
    fun providePhotosRepository(
        @ApplicationContext context: Context,
        photosApi: PhotosApiService,
    ): PhotosRepository {
        return PhotosRepositoryImpl(
            context = context,
            photosApi = photosApi,
        )
    }
}