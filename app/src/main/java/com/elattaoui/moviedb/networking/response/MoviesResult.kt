package com.elattaoui.moviedb.networking.response

import com.elattaoui.moviedb.networking.entity.MovieEntity

sealed class MoviesResult {
    data class Success(val data: List<MovieEntity>?) : MoviesResult()
    data class Error(val exception: Exception) : MoviesResult()
    object Loading : MoviesResult()
}
