package com.wesleypanaino.moviemadness.data.remote.dto

import com.google.gson.annotations.SerializedName

data class ListMovieResponseDTO(
    @SerializedName("page")
    val page: Int,
    @SerializedName("results")
    val results: List<MovieDto>
)