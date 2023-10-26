package com.wesleypanaino.moviemadness.presentation.movie_details.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.wesleypanaino.moviemadness.domain.model.MovieDetail
import com.wesleypanaino.moviemadness.presentation.ScreenEvents
import com.wesleypanaino.moviemadness.presentation.TopBarWithLogo
import com.wesleypanaino.moviemadness.presentation.ui.theme.MovieMadnessTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieDetailScreen(
    state: MovieDetailState,
    onEvent: (ScreenEvents) -> Unit,
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),

        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    TopBarWithLogo()
                },
                navigationIcon = {
                    IconButton(onClick = {
                        onEvent(ScreenEvents.GoBack)
                    }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                scrollBehavior = scrollBehavior,
            )
        },
    ) { paddingValues ->
        Surface(modifier = Modifier.padding(top = paddingValues.calculateTopPadding())) {
            MovieDetailScreenContent(
                paddingValues = paddingValues,
                state = state,
                onEvent = onEvent
            )
        }
    }
}

@Composable
fun MovieDetailScreenContent(
    paddingValues: PaddingValues,
    state: MovieDetailState,
    onEvent: (ScreenEvents) -> Unit,
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Row(modifier = Modifier.fillMaxWidth()) {
            MovieDetailItem(
                movieDetail = state.movieDetail
            )
        }

        if (state.error.isNotBlank()) {
            Text(
                text = state.error,
                color = MaterialTheme.colorScheme.error,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.Center)
            )
        }
        if (state.isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MovieDetailScreenPreview() {
    MovieMadnessTheme {
        MovieDetailScreen(
            state = MovieDetailState(
                isLoading = false,
                MovieDetail(
                    id = 1,
                    title = "Title",
                    overview = "Overview",
                    genres = emptyList(),
                    releaseDate = "21/03/2021",
                    runtime = 1,
                    voteAverage = 1.0,
                    voteCount = 1,
                    posterPath = "posterPath",
                    backdropPath = "backdropPath",
                    originalTitle = "originalTitle",
                    imdbId = "imdbId",
                    originalLanguage = "originalLanguage",
                    productionCompanies = emptyList(),
                    spokenLanguages = emptyList(),
                    tagline = "tagline"
                )
            ), {}
        )
    }
}