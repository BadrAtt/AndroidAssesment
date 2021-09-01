package com.elattaoui.moviedb.data.response

import com.elattaoui.moviedb.data.entity.MovieEntity

sealed class MoviesResult {
    data class Success(val data: List<MovieEntity>?) : MoviesResult()
    data class Error(val exception: Exception) : MoviesResult()
    object Loading : MoviesResult()
}
