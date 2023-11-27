package com.wesleypanaino.moviemadness.presentation.movie_details.components

import com.wesleypanaino.moviemadness.domain.model.MovieDetail

data class MovieDetailState(
    val isLoading: Boolean = false,
    val movieDetail: MovieDetail? = null,
    val error: String = ""
)
