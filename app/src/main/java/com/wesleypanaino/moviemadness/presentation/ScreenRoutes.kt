package com.wesleypanaino.moviemadness.presentation

sealed class ScreenRoutes(val route:String) {
    object MovieListScreen: ScreenRoutes("movie_list_screen")
    object MovieDetailScreen: ScreenRoutes("movie_detail_screen")
}