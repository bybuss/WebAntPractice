package bob.colbaskin.webantpractice.di

import bob.colbaskin.webantpractice.common.user.local.UserDataStore
import bob.colbaskin.webantpractice.common.user.local.UserPreferencesRepository
import bob.colbaskin.webantpractice.common.user.local.UserPreferencesRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideUserPreferencesRepository(dataStore: UserDataStore): UserPreferencesRepository {
        return UserPreferencesRepositoryImpl(dataStore)
    }
}