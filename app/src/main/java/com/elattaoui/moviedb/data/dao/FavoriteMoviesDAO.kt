package com.elattaoui.moviedb.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.elattaoui.moviedb.data.entity.FavoriteMovieEntity

@Dao
interface FavoriteMoviesDAO {

    @Query("DELETE FROM favorites WHERE favoriteMovieId = :id")
    fun deleteFavoriteMovieById(id: Int?)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addMovieToFavorites(favoriteMovie: FavoriteMovieEntity)

    @Query("SELECT * FROM favorites ORDER BY Id DESC")
    fun getAll(): List<FavoriteMovieEntity>?
}
