package com.elattaoui.moviedb.ui.views

import com.elattaoui.moviedb.data.entity.MovieEntity

interface MovieViewListener {
    fun onFavoriteBtnClicked(movie: MovieEntity)
}
