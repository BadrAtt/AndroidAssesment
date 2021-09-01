package com.elattaoui.moviedb.ui.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.elattaoui.moviedb.data.repository.MoviesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FavoritesFragmentViewModel @Inject constructor(
    private val moviesRepository: MoviesRepository
) : ViewModel() {

    val favoriteMovies = liveData {
        val favoriteMovies = moviesRepository.getCachedFavoriteMovies()
        favoriteMovies?.let {
            emit(favoriteMovies)
        }
    }
}
