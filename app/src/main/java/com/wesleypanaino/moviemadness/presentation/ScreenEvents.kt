package com.wesleypanaino.moviemadness.presentation

sealed class ScreenEvents {
    data class Navigate(val route: String) : ScreenEvents()
    data class ShowSnackBar(val message: String) : ScreenEvents()
    object GoBack : ScreenEvents()
    object Refresh : ScreenEvents()
    object NextPage : ScreenEvents()
    object PreviousPage : ScreenEvents()
    object ToggleViewMode : ScreenEvents()
}