package com.elattaoui.moviedb.ui.home

import com.airbnb.epoxy.TypedEpoxyController
import com.elattaoui.moviedb.networking.entity.MovieEntity
import com.elattaoui.moviedb.ui.views.loaderView
import com.elattaoui.moviedb.ui.views.movieView

class MoviesController : TypedEpoxyController<List<MovieEntity>>() {

    override fun buildModels(movies: List<MovieEntity>?) {
        movies?.forEach { movie ->
            movieView {
                id(movie.id).bind(movie)
            }
        }
        loaderView {
            id("loader")
            spanSizeOverride { totalSpanCount, _, _ -> totalSpanCount * 2 }
        }
    }
}
