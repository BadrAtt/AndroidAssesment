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
import com.elattaoui.moviedb.databinding.FragmentHomeBinding
import com.elattaoui.moviedb.networking.entity.MovieEntity
import com.elattaoui.moviedb.networking.response.MoviesResult
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {

    private val viewModel by viewModels<HomeFragmentViewModel>()
    private lateinit var binding: FragmentHomeBinding
    private val controller by lazy {
        MoviesController()
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
                    viewModel.searchMovies(query, 1)
                }
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                if (newText.isNotEmpty() && newText.length > 2) {
                    viewModel.searchMovies(newText, 1)
                }
                if (newText.isBlank()) {
                    viewModel.getPopularMovies()
                }
                return true
            }
        })


        viewModel.getPopularMovies()
        subscribe()
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
        binding.moviesRecycler.adapter = controller.adapter
        binding.moviesRecycler.layoutManager = GridLayoutManager(requireContext(), 2)
    }

    private fun bindData(movies: List<MovieEntity>?) {
        controller.setData(movies)
    }
}
