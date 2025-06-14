package bob.colbaskin.webantpractice.di

import android.content.Context
import android.util.Log
import bob.colbaskin.webantpractice.BuildConfig
import bob.colbaskin.webantpractice.auth.domain.token.RefreshTokenRepository
import bob.colbaskin.webantpractice.di.token.TokenAuthenticator
import bob.colbaskin.webantpractice.di.token.TokenInterceptor
import bob.colbaskin.webantpractice.di.token.TokenManager
import com.franmontiel.persistentcookiejar.PersistentCookieJar
import com.franmontiel.persistentcookiejar.cache.SetCookieCache
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Lazy
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RemoteModule {

    @Provides
    @Singleton
    @Named("apiUrl")
    fun provideApiUrl(): String {
        return BuildConfig.BASE_API_URL
    }

    @Provides
    @Singleton
    fun provideTokenInterceptor(tokenManager: TokenManager): TokenInterceptor {
        return TokenInterceptor(tokenManager)
    }

    @Provides
    @Singleton
    fun provideTokenAuthenticator(
        tokenManager: TokenManager,
        refreshTokenRepository: RefreshTokenRepository
    ): TokenAuthenticator {
        return TokenAuthenticator(
            refreshTokenRepository = refreshTokenRepository,
            tokenManager = tokenManager
        )
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        @ApplicationContext context: Context,
        tokenInterceptor: TokenInterceptor,
        tokenAuthenticator: Lazy<TokenAuthenticator>
    ): OkHttpClient {
        val cookieJar = PersistentCookieJar(
            SetCookieCache(),
            SharedPrefsCookiePersistor(context)
        )

        return OkHttpClient.Builder()
            .cookieJar(cookieJar)
            .addInterceptor(HttpLoggingInterceptor().apply {
                setLevel(HttpLoggingInterceptor.Level.BODY)
            })
            .addInterceptor(tokenInterceptor)
            .addInterceptor { chain ->
                val request = chain.request()
                Log.d("Cookies", "Sending cookies: ${request.headers["Cookie"]}")
                val response = chain.proceed(request)
                Log.d("Cookies", "Received cookies: ${response.headers["Set-Cookie"]}")
                response
            }
            .authenticator { route, response ->
                tokenAuthenticator.get().authenticate(route, response)
            }
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(
        @Named("apiUrl") apiUrl: String,
        okHttpClient: OkHttpClient
    ): Retrofit {
        val jsonConfig = Json {
            ignoreUnknownKeys = true
        }

        return Retrofit.Builder()
            .baseUrl(apiUrl)
            .client(okHttpClient)
            .addConverterFactory(jsonConfig.asConverterFactory("application/ld+json".toMediaType()))
            .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
            .build()
    }
}