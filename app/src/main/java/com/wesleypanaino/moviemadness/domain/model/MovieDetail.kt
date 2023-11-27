package com.wesleypanaino.moviemadness.domain.model

import com.google.gson.annotations.SerializedName
import com.wesleypanaino.moviemadness.data.remote.dto.Genre
import com.wesleypanaino.moviemadness.data.remote.dto.ProductionCompany
import com.wesleypanaino.moviemadness.data.remote.dto.SpokenLanguage

data class MovieDetail(
    @SerializedName("backdrop_path")
    val backdropPath: String,
    val genres: List<Genre>,
    val id: Int,
    @SerializedName("imdb_id")
    val imdbId: String,
    @SerializedName("original_language")
    val originalLanguage: String,
    @SerializedName("original_title")
    val originalTitle: String,
    val overview: String,
    @SerializedName("poster_path")
    val posterPath: String,
    @SerializedName("production_companies")
    val productionCompanies: List<ProductionCompany>,
    @SerializedName("release_date")
    val releaseDate: String,
    val runtime: Int,
    @SerializedName("spoken_languages")
    val spokenLanguages: List<SpokenLanguage>,
    val tagline: String,
    val title: String,
    @SerializedName("vote_average")
    val voteAverage: Double,
    @SerializedName("vote_count")
    val voteCount: Int
)
