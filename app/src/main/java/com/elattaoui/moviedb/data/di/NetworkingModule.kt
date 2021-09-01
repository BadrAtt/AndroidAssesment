package com.elattaoui.moviedb.data.di

import com.elattaoui.moviedb.data.Api
import com.elattaoui.moviedb.data.DefaultQueryParamsInterceptor
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.moczul.ok2curl.CurlInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import timber.log.Timber
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkingModule {

    @Singleton
    @Provides
    internal fun providesOkHttp(
        defaultQueryParamsInterceptor: DefaultQueryParamsInterceptor
    ): OkHttpClient {

        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(defaultQueryParamsInterceptor)
            .addInterceptor(CurlInterceptor {
                Timber.d("Ok2Curl $it ")
            })
            .build()

    }


    @Singleton
    @Provides
    fun providesApi(httpClient: OkHttpClient): Api {
        val contentType = "application/json".toMediaType()
        val json = Json {
            isLenient = true
            ignoreUnknownKeys = true
        }

        return Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .addConverterFactory(json.asConverterFactory(contentType))
            .client(httpClient)
            .build()
            .create(Api::class.java)
    }
}
