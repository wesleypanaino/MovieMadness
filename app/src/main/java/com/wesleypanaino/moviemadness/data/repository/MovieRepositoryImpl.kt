package com.wesleypanaino.moviemadness.data.repository

import android.util.Log
import com.wesleypanaino.moviemadness.common.Resource
import com.wesleypanaino.moviemadness.data.remote.TheMovieDataBaseApi
import com.wesleypanaino.moviemadness.data.remote.dto.toMovie
import com.wesleypanaino.moviemadness.data.remote.dto.toMovieDetail
import com.wesleypanaino.moviemadness.domain.model.Movie
import com.wesleypanaino.moviemadness.domain.model.MovieDetail
import com.wesleypanaino.moviemadness.domain.repository.MovieRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val api: TheMovieDataBaseApi
) : MovieRepository {

private val TAG= "MovieRepositoryImpl"
    override suspend fun getMovies(page: Int): Flow<Resource<List<Movie>>> = flow {
        try {
            emit(Resource.Loading())
            val movies = api.getMovies(page).results.map { it.toMovie() }
            emit(Resource.Success(movies))
        } catch (e: HttpException) {
            Log.e(TAG,"HttpException getMovies: ${e.localizedMessage}")
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        } catch (e: IOException) {
            Log.e(TAG,"IOException getMovies: ${e.localizedMessage}")
            emit(Resource.Error("Unable to reach server. Please check your internet connection."))
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun getMovieDetailByID(movieId: String): Flow<Resource<MovieDetail>> = flow {
        try {
            emit(Resource.Loading())
            val movies = api.getMovieDetailByID(movieId).toMovieDetail()
            emit(Resource.Success(movies))
        } catch (e: HttpException) {
            Log.e(TAG,"HttpException getMovieDetailByID: ${e.localizedMessage}")
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        } catch (e: IOException) {
            Log.e(TAG,"IOException getMovieDetailByID: ${e.localizedMessage}")
            emit(Resource.Error("Unable to reach server. Please check your internet connection."))
        }
    }.flowOn(Dispatchers.IO)
}