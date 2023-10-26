package com.wesleypanaino.moviemadness.presentation.movie_details.components

import android.graphics.Bitmap
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.palette.graphics.Palette
import coil.compose.AsyncImage
import com.wesleypanaino.moviemadness.R
import com.wesleypanaino.moviemadness.common.Constants
import com.wesleypanaino.moviemadness.data.remote.dto.Genre
import com.wesleypanaino.moviemadness.domain.model.MovieDetail
import com.wesleypanaino.moviemadness.presentation.ui.theme.MovieMadnessTheme


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun MovieDetailItem(
    movieDetail: MovieDetail?
) {
    if (movieDetail == null) return

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
            .background(MaterialTheme.colorScheme.background),
    ) {
        //todo show loading indicator whilst image is loading
        AsyncImage(
            model = "${Constants.IMAGE_URL_WITH_WIDTH}${movieDetail.backdropPath}",
            modifier = Modifier
                .fillMaxWidth(),
            contentDescription = null,
            placeholder = painterResource(R.drawable.poster_placeholder),
        )
        Text(
            text = movieDetail.title,
            style = MaterialTheme.typography.titleLarge,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(4.dp),
            color = MaterialTheme.colorScheme.onBackground
        )
        Divider()
        Row(
            modifier=Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween) {
            Text(
                text = "Release Date: ${movieDetail.releaseDate}",
                style = MaterialTheme.typography.titleSmall,
                modifier = Modifier
                    .padding(4.dp),
                color = MaterialTheme.colorScheme.onBackground
            )
            Text(
                text = "Runtime: ${movieDetail.runtime} min",
                style = MaterialTheme.typography.titleSmall,
                modifier = Modifier
                    .padding(4.dp),
                color = MaterialTheme.colorScheme.onBackground
            )
            Text(
                text = "Rating: ${String.format("%.1f", movieDetail.voteAverage)}",
                style = MaterialTheme.typography.titleSmall,
                modifier = Modifier
                    .padding(4.dp),
                color = MaterialTheme.colorScheme.onBackground
            )
        }
        Divider()
        Spacer(modifier = Modifier.height(15.dp))
        //if not released show release date else show "out now"
        Box(
            modifier = Modifier
                .align(Alignment.Start)
        ) {
            Text(
                text = movieDetail.overview,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier
                    .padding(4.dp),
                color = MaterialTheme.colorScheme.onBackground
            )
        }
        Spacer(modifier = Modifier.height(15.dp))
        Divider()
        Spacer(modifier = Modifier.height(15.dp))
        FlowRow(
            verticalArrangement = Arrangement.spacedBy(4.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally)
        ) {
            movieDetail.genres.forEach { genre ->
                Tag(tag = genre.name)
            }
        }
    }
}

//Preview for MovieDetailItem
@Preview(showBackground = true)
@Composable
fun MovieDetailItemPreview() {
    MovieMadnessTheme {
        val listOfGenre= listOf<Genre>(Genre(1, "Action"), Genre(2, "Drama"))
        MovieDetailItem(
            movieDetail = MovieDetail(
                backdropPath = "/tC78Pck2YCsUAtEdZwuHYUFYtOj.jpg",
                genres = listOfGenre,
                id = 926393,
                imdbId = "",
                originalLanguage = "",
                originalTitle = "",
                overview = "Very Long overviewww",
                posterPath = "/b0Ej6fnXAP8fK75hlyi2jKqdhHz.jpg",
                productionCompanies = emptyList(),
                releaseDate = "2021-03-02",
                runtime = 152,
                spokenLanguages = emptyList(),
                tagline = "",
                title = "The Equalizer 3",
                voteAverage = 1.0,
                voteCount = 1
            )
        )
    }
}

//ToDo on click show similar genre's movies
@Composable
fun Tag(
    tag: String
) {
    Box(
        modifier = Modifier
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.primary,
                shape = RoundedCornerShape(100.dp)
            )
            .padding(10.dp)
    ) {
        Text(
            text = tag,
            color = MaterialTheme.colorScheme.primary,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodySmall
        )
    }
}