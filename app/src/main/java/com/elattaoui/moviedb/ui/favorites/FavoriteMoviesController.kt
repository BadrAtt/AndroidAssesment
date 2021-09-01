package com.elattaoui.moviedb.ui.favorites

import com.airbnb.epoxy.TypedEpoxyController
import com.elattaoui.moviedb.data.entity.FavoriteMovieEntity
import com.elattaoui.moviedb.ui.views.MovieView
import com.elattaoui.moviedb.ui.views.MovieViewListener
import com.elattaoui.moviedb.ui.views.movieView

class FavoriteMoviesController(val listener: MovieViewListener) :
    TypedEpoxyController<List<FavoriteMovieEntity>>() {

    override fun buildModels(data: List<FavoriteMovieEntity>?) {
        data?.forEach {
            movieView {
                id(it.favoriteMovieId)
                    .bind(
                        MovieView.MovieViewData(
                            it.movie!!,
                            this@FavoriteMoviesController.listener
                        )
                    )
            }
        }
    }
}
