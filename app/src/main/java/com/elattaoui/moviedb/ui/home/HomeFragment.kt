package com.elattaoui.moviedb.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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
        viewModel.popularMovies.observe(viewLifecycleOwner, { moviesResult ->
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

    private fun bindData(movies: List<MovieEntity>?) {
        binding.moviesRecycler.adapter = controller.adapter
        binding.moviesRecycler.layoutManager = GridLayoutManager(requireContext(), 2)
        controller.setData(movies)
    }
}
