package com.elattaoui.moviedb.networking.response

import com.elattaoui.moviedb.networking.entity.MovieEntity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PageResponse(
    @SerialName("page") val page: Int,
    @SerialName("total_results") var totalResults: Int,
    @SerialName("total_pages") var totalPages: Int,
    @SerialName("results") var results: List<MovieEntity>? = null
)
