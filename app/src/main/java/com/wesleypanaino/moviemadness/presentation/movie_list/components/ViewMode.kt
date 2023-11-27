package com.wesleypanaino.moviemadness.presentation.movie_list.components

enum class ViewMode {
    LIST,
    GRID;

    fun next(): ViewMode {
        return when (this) {
            LIST -> GRID
            GRID -> LIST
        }
    }
}