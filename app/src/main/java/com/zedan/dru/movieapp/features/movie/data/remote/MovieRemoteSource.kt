package com.zedan.dru.movieapp.features.movie.data.remote

import com.zedan.dru.movieapp.features.movie.data.remote.models.MovieListResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieRemoteSource {

    @GET("movie/{category}")
    suspend fun getMovieList(
        @Path("category") category: String,
        @Query("page") page: Int,
    ): MovieListResponse
}
