package com.elattaoui.moviedb.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.elattaoui.moviedb.data.converter.Converters
import com.elattaoui.moviedb.data.dao.MoviesDAO
import com.elattaoui.moviedb.data.entity.MovieEntity

@Database(
    entities = [MovieEntity::class],
    version = 1
)
@TypeConverters(Converters::class)
abstract class AppDataBase : RoomDatabase() {

    abstract fun moviesDao(): MoviesDAO
}
