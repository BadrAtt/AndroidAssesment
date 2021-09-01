package com.elattaoui.moviedb.data.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@Serializable
@Entity(tableName = "favorites")
data class FavoriteMovieEntity(
    @PrimaryKey(autoGenerate = true)
    val favoriteMovieId: Int? = null,
    @Embedded
    val movie: MovieEntity? = null
) {
    override fun toString(): String {
        return Json.encodeToString(serializer(), this)
    }
}
