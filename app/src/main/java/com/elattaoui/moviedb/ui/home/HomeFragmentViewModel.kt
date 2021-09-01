package com.elattaoui.moviedb.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elattaoui.moviedb.data.entity.MovieEntity
import com.elattaoui.moviedb.data.repository.MoviesRepository
import com.elattaoui.moviedb.data.repository.SearchMoviesRepository
import com.elattaoui.moviedb.data.response.MoviesResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeFragmentViewModel @Inject constructor(
    private val moviesRepository: MoviesRepository,
    private val searchMoviesRepository: SearchMoviesRepository
) : ViewModel() {

    private var moviesLiveData: MutableLiveData<MoviesResult> = MutableLiveData()
    val movies: LiveData<MoviesResult> = moviesLiveData

    private var moviesList = mutableListOf<MovieEntity>()

    fun getPopularMovies(pageNumber: Int) {
        viewModelScope.launch {
            moviesRepository.fetchPopularMovies(pageNumber).collect { moviesResult ->
                moviesLiveData.value = moviesResult
                if (moviesResult is MoviesResult.Success) {
                    moviesResult.data?.let {
                        moviesList = moviesResult.data.toMutableList()
                    }
                }
            }
        }
    }

    fun searchMovies(query: String) {
        viewModelScope.launch {
            searchMoviesRepository.searchMovies(query).collect { result ->
                moviesLiveData.value = result
            }
        }
    }

    fun handleFavoriteMovie(movie: MovieEntity) {
        viewModelScope.launch {
            if (movie.isFavorite) {
                moviesRepository.removeMovieFromFavorites(movie).collect {
                    movie.isFavorite = it
                }
            } else {
                moviesRepository.addMovieToFavorites(movie).collect {
                    movie.isFavorite = it
                }
            }
            moviesList.find { it.id == movie.id }?.isFavorite = movie.isFavorite
            //todo: update view
        }
    }
}
