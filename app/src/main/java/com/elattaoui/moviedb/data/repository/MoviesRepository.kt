package com.elattaoui.moviedb.data.repository

import com.elattaoui.moviedb.data.Api
import com.elattaoui.moviedb.data.dao.FavoriteMoviesDAO
import com.elattaoui.moviedb.data.dao.MoviesDAO
import com.elattaoui.moviedb.data.entity.FavoriteMovieEntity
import com.elattaoui.moviedb.data.entity.MovieEntity
import com.elattaoui.moviedb.data.response.MoviesResult
import dagger.Reusable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

@Reusable
class MoviesRepository @Inject constructor(
    private val api: Api,
    private val moviesDAO: MoviesDAO,
    private val favoriteMoviesDAO: FavoriteMoviesDAO
) {

    fun fetchPopularMovies(pageNumber: Int): Flow<MoviesResult> {
        return flow {
            try {
                val cachedResponse = getCachedMovies()
                emit(
                    MoviesResult.Success(
                        cachedResponse
                    )
                )
                val networkResult = api.getPopularMovies(pageNumber = pageNumber)
                emit(
                    MoviesResult.Success(
                        networkResult.results?.sortedBy { movie ->
                            movie.originalTitle
                        })
                )
                networkResult.results?.map { movie ->
                    storeMovie(movie)
                }
            } catch (e: Exception) {
                Timber.e(e)
                emit(MoviesResult.Error(e))
            }
        }.onStart { emit(MoviesResult.Loading) }
    }

    suspend fun getCachedFavoriteMovies(): List<FavoriteMovieEntity>? {
        return withContext(Dispatchers.IO) {
            favoriteMoviesDAO.getAll()
        }
    }

    suspend fun addMovieToFavorites(movie: MovieEntity): Flow<Boolean> {
        return flow {
            try {
                val favoriteMovie = FavoriteMovieEntity(favoriteMovieId = movie.id, movie = movie)
                favoriteMoviesDAO.addMovieToFavorites(favoriteMovie)
                emit(true)
            } catch (e: java.lang.Exception) {
                Timber.e(e)
                emit(false)
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun removeMovieFromFavorites(movie: MovieEntity): Flow<Boolean> {
        return flow {
            try {
                favoriteMoviesDAO.deleteFavoriteMovieById(movie.id)
                emit(true)
            } catch (e: java.lang.Exception) {
                Timber.e(e)
                emit(false)
            }
        }.flowOn(Dispatchers.IO)
    }


    private suspend fun getCachedMovies(): List<MovieEntity>? {
        return withContext(Dispatchers.IO) {
            moviesDAO.getAll()
        }
    }

    private suspend fun storeMovie(movie: MovieEntity) {
        withContext(Dispatchers.IO) {
            moviesDAO.insert(movie)
        }
    }
}
