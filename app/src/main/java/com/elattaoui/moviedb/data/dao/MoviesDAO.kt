package com.elattaoui.moviedb.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.elattaoui.moviedb.data.entity.MovieEntity

@Dao
interface MoviesDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(movie: MovieEntity)

    @Query("SELECT * FROM movies ORDER BY Id DESC")
    fun getAll(): List<MovieEntity>?
}
