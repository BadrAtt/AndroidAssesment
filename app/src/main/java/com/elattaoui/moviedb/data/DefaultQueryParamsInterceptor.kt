package com.elattaoui.moviedb.data

import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DefaultQueryParamsInterceptor @Inject constructor() : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        val url = request.url.newBuilder()
            .addQueryParameter("api_key", "9d5ffb54bee2084b45bd97b7a770905f")
            .addQueryParameter("sort_by", "popularity.desc")
            .build()
        request = request.newBuilder().url(url).build()
        return chain.proceed(request)
    }
}
