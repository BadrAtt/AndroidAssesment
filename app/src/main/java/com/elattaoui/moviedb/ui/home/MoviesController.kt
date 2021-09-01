package com.elattaoui.moviedb.ui.home

import com.airbnb.epoxy.TypedEpoxyController
import com.elattaoui.moviedb.data.entity.MovieEntity
import com.elattaoui.moviedb.ui.views.*

class MoviesController(private val listener: MovieViewListener) :
    TypedEpoxyController<List<MovieEntity>>() {

    override fun buildModels(movies: List<MovieEntity>?) {
        movies?.forEach { movie ->
            movieView {
                id(movie.id)
                    .bind(
                        MovieView.MovieViewData(
                            movie = movie,
                            listener = this@MoviesController.listener
                        )
                    )
            }
        }
        loaderView {
            id("loader")
            spanSizeOverride { totalSpanCount, _, _ -> totalSpanCount * 2 }
        }
    }
}
