package com.elattaoui.moviedb.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.elattaoui.moviedb.R
import com.elattaoui.moviedb.data.entity.MovieEntity
import com.elattaoui.moviedb.data.response.MoviesResult
import com.elattaoui.moviedb.databinding.FragmentHomeBinding
import com.elattaoui.moviedb.ui.views.MovieViewListener
import com.elattaoui.moviedb.utils.EndlessRecyclerViewScrollListener
import com.elattaoui.moviedb.utils.GridEqualSpaceItemDecoration
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home), MovieViewListener {

    private val viewModel by viewModels<HomeFragmentViewModel>()
    private lateinit var binding: FragmentHomeBinding
    private var moviesList = arrayListOf<MovieEntity>()
    private val controller by lazy {
        MoviesController(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()

        binding.moviesSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                if (query.isNotEmpty() && query.length > 2) {
                    viewModel.searchMovies(query)
                }
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                if (newText.isNotEmpty() && newText.length > 2) {
                    viewModel.searchMovies(newText)
                }
                if (newText.isBlank()) {
                    viewModel.getPopularMovies(1)
                }
                return true
            }
        })

        viewModel.getPopularMovies(1)
        subscribe()
    }

    override fun onFavoriteBtnClicked(movie: MovieEntity) {
        viewModel.handleFavoriteMovie(movie)
    }

    private fun subscribe() {
        viewModel.movies.observe(viewLifecycleOwner, { moviesResult ->
            when (moviesResult) {
                is MoviesResult.Success -> {
                    bindData(moviesResult.data)
                }
                is MoviesResult.Loading -> {
                }
                is MoviesResult.Error -> {
                    Toast.makeText(
                        requireContext(),
                        "something went wrong",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        })
    }

    private fun setupRecyclerView() {
        val itemSpacing = resources.getDimension(R.dimen.movie_item_spacing)
        val itemDecoration = GridEqualSpaceItemDecoration(itemSpacing.toInt())
        val recyclerViewLayoutManager = GridLayoutManager(requireContext(), 2).apply {
            spanSizeLookup = controller.spanSizeLookup
        }
        binding.moviesRecycler.apply {
            adapter = controller.adapter
            layoutManager = recyclerViewLayoutManager
            if (itemDecorationCount == 0) {
                addItemDecoration(itemDecoration)
            }
            addOnScrollListener(object :
                EndlessRecyclerViewScrollListener(recyclerViewLayoutManager) {
                override fun onLoadMore(page: Int) {
                    viewModel.getPopularMovies(page)
                }
            })
        }
    }

    private fun bindData(movies: List<MovieEntity>?) {
        movies?.let {
            moviesList.addAll(movies)
            controller.setData(moviesList)
        }

    }
}
