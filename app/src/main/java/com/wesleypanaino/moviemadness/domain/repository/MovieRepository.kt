package com.wesleypanaino.moviemadness.domain.repository

import com.wesleypanaino.moviemadness.common.Resource
import com.wesleypanaino.moviemadness.domain.model.Movie
import com.wesleypanaino.moviemadness.domain.model.MovieDetail
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    suspend fun getMovies(page:Int): Flow<Resource<List<Movie>>>
    suspend fun getMovieDetailByID(movieId:String): Flow<Resource<MovieDetail>>
}