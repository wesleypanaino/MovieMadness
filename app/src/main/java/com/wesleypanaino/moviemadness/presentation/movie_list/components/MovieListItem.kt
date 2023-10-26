package com.wesleypanaino.moviemadness.presentation.movie_list.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.wesleypanaino.moviemadness.R
import com.wesleypanaino.moviemadness.common.Constants
import com.wesleypanaino.moviemadness.domain.model.Movie

@Composable
fun MovieListItem(
    movie: Movie,
    onItemClick: (Movie) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background)
            .padding(4.dp)
            .clickable { onItemClick(movie) },
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Box(
            modifier = Modifier
                .wrapContentWidth()
                .padding(4.dp)
                .clip(RoundedCornerShape(16.dp))
        ) {
            //todo show loading whilst image is loading
            AsyncImage(
                model = "${Constants.IMAGE_URL_WITH_WIDTH}${movie.posterPath}",
                contentDescription = null,
                placeholder = painterResource(R.drawable.placeholder),
            )
        }
        //todo add banner for if not released show release date else show "out now"
    }
}

@Preview
@Composable
fun MovieListItemPreview() {
    MovieListItem(
        Movie(
            id = 926393,
            title = "The Equalizer 3",
            overview = "Overview 1",
            releaseDate = "2021-01-01",
            posterPath = "/b0Ej6fnXAP8fK75hlyi2jKqdhHz.jpg",
            backdropPath = "/tC78Pck2YCsUAtEdZwuHYUFYtOj.jpg",
            voteAverage = 1.0,
            voteCount = 1,
            originalTitle = "The Equalizer 3",
            genreIds = listOf(
                1, 2
            )
        ),
        {}
    )
}