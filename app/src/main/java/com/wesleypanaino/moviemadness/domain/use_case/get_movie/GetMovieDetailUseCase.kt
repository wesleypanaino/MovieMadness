package com.wesleypanaino.moviemadness.domain.use_case.get_movie


import com.wesleypanaino.moviemadness.common.Resource
import com.wesleypanaino.moviemadness.domain.model.MovieDetail
import com.wesleypanaino.moviemadness.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMovieDetailUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(movieId: String): Flow<Resource<MovieDetail>> =
        movieRepository.getMovieDetailByID(movieId)
}