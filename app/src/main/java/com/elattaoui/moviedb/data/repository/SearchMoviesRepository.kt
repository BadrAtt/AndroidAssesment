package com.elattaoui.moviedb.data.repository

import com.elattaoui.moviedb.data.Api
import com.elattaoui.moviedb.data.response.MoviesResult
import dagger.Reusable
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

@Reusable
class SearchMoviesRepository @Inject constructor(
    private val api: Api
) {

    fun searchMovies(query: String): Flow<MoviesResult> {
        return flow {
            try {
                val result = api.searchMovies(
                    query = query
                )
                emit(
                    MoviesResult.Success(
                        result.results?.sortedBy { movie ->
                            movie.originalTitle
                        })
                )
            } catch (e: Exception) {
                emit(MoviesResult.Error(e))
            }

        }.onStart { emit(MoviesResult.Loading) }
    }
}
