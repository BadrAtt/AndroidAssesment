package com.elattaoui.moviedb.data.dao

import androidx.room.*
import com.elattaoui.moviedb.data.entity.MovieEntity

@Dao
interface MoviesDAO {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(customEntity: MovieEntity)

    @Update
    fun update(customEntity: MovieEntity)

    @Delete
    fun delete(customEntity: MovieEntity)

    @Query("DELETE FROM MoviesDb WHERE Id = :id")
    fun deleteMovieById(id: Int?)

    @Query("DELETE FROM MoviesDb")
    fun deleteAll()

    @Query("SELECT * FROM MoviesDb ORDER BY Id DESC")
    fun getAll(): List<MovieEntity>?
}
