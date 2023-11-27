package com.wesleypanaino.moviemadness.presentation.movie_list.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.wesleypanaino.moviemadness.domain.model.Movie

@Composable
fun MovieListItemBasic(
    movie: Movie,
    onItemClick: (Movie) -> Unit
) {
    Box(
        modifier = Modifier
            .wrapContentWidth()
            .padding(4.dp)
            .clip(RoundedCornerShape(8.dp))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.primaryContainer)
                .padding(4.dp)
                .clickable { onItemClick(movie) },
            verticalArrangement = Arrangement.SpaceBetween
        )
        {
            Text(
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                text = movie.title,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(4.dp)
            )
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween){
                Text(
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    text = "Release Date: ${movie.releaseDate}",
                    style = MaterialTheme.typography.labelSmall,
                    modifier = Modifier.padding(4.dp)
                )
                Text(
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    text = "Rating: ${String.format("%.1f", movie.voteAverage)}",
                    style = MaterialTheme.typography.labelSmall,
                    modifier = Modifier.padding(4.dp)
                )
            }
        }
    }
}

@Preview
@Composable
fun MovieListItemBasicPreview() {
    MovieListItemBasic(movie =
    Movie(
        id = 299054,
        title = "Expend4bles",
        overview = "Overview 2",
        releaseDate = "2021-01-02",
        posterPath = "/mOX5O6JjCUWtlYp5D8wajuQRVgy.jpg",
        backdropPath = "/rMvPXy8PUjj1o8o1pzgQbdNCsvj.jpg",
        voteAverage = 2.0,
        voteCount = 2,
        originalTitle = "Expend4bles",
        genreIds = listOf(
            1, 2
        )
    ), {})
}