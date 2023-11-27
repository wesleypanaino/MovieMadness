package com.wesleypanaino.moviemadness.data.remote

import com.wesleypanaino.moviemadness.data.remote.dto.MovieDetailDto
import com.wesleypanaino.moviemadness.data.remote.dto.ListMovieResponseDTO
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TheMovieDataBaseApi {
    @GET("/3/discover/movie")
    suspend fun getMovies( @Query("page") page: Int): ListMovieResponseDTO

    @GET("/3/movie/{movie_id}")
    suspend fun getMovieDetailByID(@Path("movie_id") movieId: String): MovieDetailDto
}