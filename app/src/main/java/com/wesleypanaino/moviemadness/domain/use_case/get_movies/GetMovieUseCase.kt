package com.wesleypanaino.moviemadness.domain.use_case.get_movies


import com.wesleypanaino.moviemadness.common.Resource
import com.wesleypanaino.moviemadness.domain.model.Movie
import com.wesleypanaino.moviemadness.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMovieUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(page: Int): Flow<Resource<List<Movie>>> =
        movieRepository.getMovies(page)
}