package com.wesleypanaino.moviemadness.presentation.movie_list.components

import com.wesleypanaino.moviemadness.domain.model.Movie

data class MovieListState(
    val isLoading: Boolean = false,
    val movies: List<Movie> = emptyList(),
    val error: String = "",
    val viewMode: ViewMode
)
