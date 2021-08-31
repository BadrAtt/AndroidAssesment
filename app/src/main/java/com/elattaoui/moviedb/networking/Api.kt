package com.elattaoui.moviedb.networking

import com.elattaoui.moviedb.networking.response.PageResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {

    @GET("discover/movie?sort_by=popularity.desc")
    suspend fun getPopularMovies(
        @Query("page") pageNumber: Int
    ): PageResponse

    @GET("search/tv?sort_by=popularity.desc")
    suspend fun searchMovies(
        @Query("page") pageNumber: Int,
        @Query("query") query: String
    ): PageResponse
}
