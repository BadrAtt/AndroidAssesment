package com.elattaoui.moviedb.networking.repository

import com.elattaoui.moviedb.networking.Api
import com.elattaoui.moviedb.networking.response.MoviesResult
import dagger.Reusable
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

@Reusable
class PopularMoviesRepository @Inject constructor(
    private val api: Api
) {

    fun fetchPopularMovies(pageNumber: Int): Flow<MoviesResult> {
        return flow {
            try {
                val result = api.getPopularMovies(pageNumber = pageNumber)
                emit(MoviesResult.Success(result.results))
            } catch (e: Exception) {
                emit(MoviesResult.Error(e))
            }
        }.onStart { emit(MoviesResult.Loading) }
    }
}