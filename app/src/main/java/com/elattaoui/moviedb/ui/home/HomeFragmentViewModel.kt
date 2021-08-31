package com.elattaoui.moviedb.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elattaoui.moviedb.networking.repository.PopularMoviesRepository
import com.elattaoui.moviedb.networking.repository.SearchMoviesRepository
import com.elattaoui.moviedb.networking.response.MoviesResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeFragmentViewModel @Inject constructor(
    private val popularMoviesRepository: PopularMoviesRepository,
    private val searchMoviesRepository: SearchMoviesRepository
) : ViewModel() {

    private var moviesLiveData: MutableLiveData<MoviesResult> = MutableLiveData()
    val movies: LiveData<MoviesResult> = moviesLiveData

    fun getPopularMovies() {
        viewModelScope.launch {
            popularMoviesRepository.fetchPopularMovies(1).collect { moviesResult ->
                moviesLiveData.value = moviesResult
            }
        }
    }

    fun searchMovies(query: String, pageNumber: Int) {
        viewModelScope.launch {
            searchMoviesRepository.searchMovies(query, pageNumber).collect { result ->
                moviesLiveData.value = result
            }
        }
    }
}
