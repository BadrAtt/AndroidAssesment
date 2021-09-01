package com.elattaoui.moviedb.ui.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.elattaoui.moviedb.R
import com.elattaoui.moviedb.data.entity.MovieEntity
import com.elattaoui.moviedb.databinding.FragmentFavoritesBinding
import com.elattaoui.moviedb.ui.views.MovieViewListener
import com.elattaoui.moviedb.utils.GridEqualSpaceItemDecoration
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoritesFragment : Fragment(R.layout.fragment_favorites), MovieViewListener {

    private lateinit var binding: FragmentFavoritesBinding
    private val viewModel by viewModels<FavoritesFragmentViewModel>()
    private var controller: FavoriteMoviesController? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        controller = FavoriteMoviesController(this)
        setupRecyclerView()
        viewModel.favoriteMovies.observe(viewLifecycleOwner, { favoriteMovies ->
            controller?.setData(favoriteMovies)
        })
    }

    private fun setupRecyclerView() {
        val itemSpacing = resources.getDimension(R.dimen.movie_item_spacing)
        val itemDecoration = GridEqualSpaceItemDecoration(itemSpacing.toInt())
        val recyclerViewLayoutManager = GridLayoutManager(requireContext(), 2)
        binding.favoritesRecycler.apply {
            adapter = controller?.adapter
            layoutManager = recyclerViewLayoutManager
            if (itemDecorationCount == 0) {
                addItemDecoration(itemDecoration)
            }
        }
    }

    override fun onFavoriteBtnClicked(movie: MovieEntity) {
        //todo: handle removing items from favorites
    }
}
