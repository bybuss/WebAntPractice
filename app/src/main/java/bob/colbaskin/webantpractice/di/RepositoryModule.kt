package bob.colbaskin.webantpractice.di

import bob.colbaskin.webantpractice.auth.data.AuthRepositoryImpl
import bob.colbaskin.webantpractice.auth.domain.AuthApiService
import bob.colbaskin.webantpractice.auth.domain.AuthRepository
import bob.colbaskin.webantpractice.common.user_prefs.data.local.datastore.UserDataStore
import bob.colbaskin.webantpractice.common.user_prefs.domain.UserPreferencesRepository
import bob.colbaskin.webantpractice.common.user_prefs.data.UserPreferencesRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
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
    fun provideAuthRepository(authApi: AuthApiService): AuthRepository {
        return AuthRepositoryImpl(authApi)
    }
}