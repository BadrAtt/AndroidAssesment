package com.elattaoui.moviedb.data.di

import android.content.Context
import androidx.room.Room
import com.elattaoui.moviedb.data.dao.MoviesDAO
import com.elattaoui.moviedb.data.database.AppDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataBaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context): AppDataBase {
        return Room.databaseBuilder(
            appContext,
            AppDataBase::class.java,
            "MoviesDb"
        ).build()
    }

    @Provides
    fun provideMoviesDAO(appDataBase: AppDataBase): MoviesDAO {
        return appDataBase.moviesDao()
    }
}
