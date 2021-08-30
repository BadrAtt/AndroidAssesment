package com.elattaoui.moviedb.ui.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.airbnb.epoxy.ModelProp
import com.airbnb.epoxy.ModelView
import com.bumptech.glide.Glide
import com.elattaoui.moviedb.databinding.MovieViewBinding
import com.elattaoui.moviedb.networking.entity.MovieEntity

@ModelView(autoLayout = ModelView.Size.MATCH_WIDTH_WRAP_HEIGHT)
class MovieView : ConstraintLayout {

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    private val binding = MovieViewBinding.inflate(LayoutInflater.from(context), this)

    @ModelProp
    fun bind(movie: MovieEntity) {
        binding.movieRating.text = "${movie.voteAverage}"
        binding.raters.text = "${movie.voteCount}"
        binding.movieTitle.text = movie.originalTitle

        Glide.with(context)
            .load("$POSTER_BASE_URL${movie.posterPath}")
            .into(binding.moviePoster)
    }

    companion object {
        private const val POSTER_BASE_URL = "https://image.tmdb.org/t/p/w500/"
    }
}
