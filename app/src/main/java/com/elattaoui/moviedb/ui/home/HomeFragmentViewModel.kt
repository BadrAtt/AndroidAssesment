package com.elattaoui.moviedb.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.elattaoui.moviedb.networking.repository.PopularMoviesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

@HiltViewModel
class HomeFragmentViewModel @Inject constructor(
    private val popularMoviesRepository: PopularMoviesRepository
) : ViewModel() {

    val popularMovies = liveData {
        popularMoviesRepository.fetchPopularMovies(1).collect { moviesResult ->
            emit(moviesResult)
        }
    }
}
