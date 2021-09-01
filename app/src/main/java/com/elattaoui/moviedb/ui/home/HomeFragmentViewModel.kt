package com.elattaoui.moviedb.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elattaoui.moviedb.data.repository.PopularMoviesRepository
import com.elattaoui.moviedb.data.repository.SearchMoviesRepository
import com.elattaoui.moviedb.data.response.MoviesResult
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

    fun getPopularMovies(pageNumber: Int) {
        viewModelScope.launch {
            popularMoviesRepository.fetchPopularMovies(pageNumber).collect { moviesResult ->
                moviesLiveData.value = moviesResult
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
}
