package com.wesleypanaino.moviemadness.data.remote.dto

import com.google.gson.annotations.SerializedName
import com.wesleypanaino.moviemadness.domain.model.MovieDetail

data class MovieDetailDto(
    val adult: Boolean,
    @SerializedName("backdrop_path")
    val backdropPath: String,
    @SerializedName("belongs_to_collection")
    val belongsToCollection: BelongsToCollection,
    val budget: Int,
    val genres: List<Genre>,
    val homepage: String,
    val id: Int,
    @SerializedName("imdb_id")
    val imdbId: String,
    @SerializedName("original_language")
    val originalLanguage: String,
    @SerializedName("original_title")
    val originalTitle: String,
    val overview: String,
    val popularity: Double,
    @SerializedName("poster_path")
    val posterPath: String,
    @SerializedName("production_companies")
    val productionCompanies: List<ProductionCompany>,
    @SerializedName("production_countries")
    val productionCountries: List<ProductionCountry>,
    @SerializedName("release_date")
    val releaseDate: String,
    val revenue: Int,
    val runtime: Int,
    @SerializedName("spoken_languages")
    val spokenLanguages: List<SpokenLanguage>,
    val status: String,
    val tagline: String,
    val title: String,
    val video: Boolean,
    @SerializedName("vote_average")
    val voteAverage: Double,
    @SerializedName("vote_count")
    val voteCount: Int
)

fun MovieDetailDto.toMovieDetail():MovieDetail{
    return MovieDetail(
        backdropPath = backdropPath,
        genres = genres,
        id = id,
        imdbId = imdbId,
        originalLanguage = originalLanguage,
        originalTitle = originalTitle,
        overview = overview,
        posterPath = posterPath,
        productionCompanies = productionCompanies,
        releaseDate = releaseDate,
        runtime = runtime,
        spokenLanguages = spokenLanguages,
        tagline = tagline,
        title = title,
        voteAverage = voteAverage,
        voteCount = voteCount
    )
}