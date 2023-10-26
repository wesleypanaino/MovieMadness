package com.wesleypanaino.moviemadness.presentation.movie_list.components

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.wesleypanaino.moviemadness.R
import com.wesleypanaino.moviemadness.domain.model.Movie
import com.wesleypanaino.moviemadness.presentation.ScreenEvents
import com.wesleypanaino.moviemadness.presentation.ScreenRoutes
import com.wesleypanaino.moviemadness.presentation.TopBarWithLogo
import com.wesleypanaino.moviemadness.presentation.ui.theme.MovieMadnessTheme

private val TAG = "MovieListScreen"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieListScreen(
    state: MovieListState,
    onEvent: (ScreenEvents) -> Unit,
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),

        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                ),
                title = {
                    TopBarWithLogo()
                },
                actions = {
                    IconButton(onClick = { onEvent(ScreenEvents.ShowSnackBar("TV Coming soon") )}) {
                        Icon(
                            painterResource(id = R.drawable.theaters_24px),
                            contentDescription = "Movies",
                            tint = MaterialTheme.colorScheme.onPrimaryContainer,
                        )
                    }
                    IconButton(onClick = { onEvent(ScreenEvents.ShowSnackBar("Search Coming soon")) }) {
                        Icon(
                            imageVector = Icons.Filled.Search,
                            contentDescription = "Search",
                            tint = MaterialTheme.colorScheme.onPrimaryContainer,
                        )
                    }
                    IconButton(onClick = { onEvent(ScreenEvents.ToggleViewMode )}) {
                        Icon(
                            painterResource(
                                id = when (state.viewMode) {
                                    ViewMode.LIST -> R.drawable.view_list_24px
                                    ViewMode.GRID -> R.drawable.drag_indicator_24px
                                }
                            ),
                            contentDescription = "Toggle View Mode",
                            tint = MaterialTheme.colorScheme.onPrimaryContainer,
                        )
                    }
                },
                scrollBehavior = scrollBehavior,
            )
        },
    ) { paddingValues ->
        Surface(modifier = Modifier.padding(top = paddingValues.calculateTopPadding())) {
            ScreenContent(
                paddingValues = paddingValues,
                state = state,
                onEvent = onEvent
            )
        }
    }
}

@Composable
fun ScreenContent(
    paddingValues: PaddingValues,
    state: MovieListState,
    onEvent: (ScreenEvents) -> Unit
) {
    Column {
        if (state.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                color = MaterialTheme.colorScheme.onBackground
            )
        }
        if (state.error.isNotBlank()) {
            Text(
                text = state.error,
                color = MaterialTheme.colorScheme.error,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally)
            )
        }
        when (state.viewMode) {
            ViewMode.LIST -> {
                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(state.movies) { movie ->
                        MovieListItemBasic(
                            movie = movie,
                            onItemClick = {
                                Log.i(
                                    TAG,
                                    "MovieListScreen onItemClick ${
                                        ScreenEvents.Navigate(ScreenRoutes.MovieDetailScreen.route + "/${movie.id}")
                                    }"
                                )
                                onEvent(ScreenEvents.Navigate(ScreenRoutes.MovieDetailScreen.route + "/${movie.id}"))
                            }
                        )
                        Divider()
                    }
                    if (!state.isLoading)
                        item {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {

                                IconButton(
                                    onClick = {
                                        onEvent(ScreenEvents.PreviousPage)
                                    },
                                ) {
                                    Icon(
                                        imageVector = Icons.Filled.ArrowBack,
                                        contentDescription = "Previous Page",
                                        tint = MaterialTheme.colorScheme.onPrimaryContainer,
                                    )
                                }

                                IconButton(onClick = {
                                    onEvent(ScreenEvents.NextPage)
                                }
                                ) {
                                    Icon(
                                        imageVector = Icons.Filled.ArrowForward,
                                        contentDescription = "Next Page",
                                        tint = MaterialTheme.colorScheme.onPrimaryContainer,
                                    )
                                }
                            }
                        }
                }
            }

            ViewMode.GRID -> {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2), // You can adjust the number of columns here
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(state.movies) { movie ->
                        MovieListItem(
                            movie = movie,
                            onItemClick = {
                                Log.i(
                                    TAG,
                                    "MovieListScreen onItemClick ${
                                        ScreenEvents.Navigate(
                                            ScreenRoutes.MovieDetailScreen.route + "/${movie.id}"
                                        )
                                    }"
                                )
                                onEvent(ScreenEvents.Navigate(ScreenRoutes.MovieDetailScreen.route + "/${movie.id}"))
                            }
                        )
                    }
                    if (!state.isLoading)
                        item {
                            IconButton(
                                modifier = Modifier.align(Alignment.Start),
                                onClick = {
                                    onEvent(ScreenEvents.PreviousPage)
                                },
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.ArrowBack,
                                    contentDescription = "Previous Page",
                                    tint = MaterialTheme.colorScheme.onPrimaryContainer,
                                )
                            }
                        }
                    if (!state.isLoading)
                        item {
                            IconButton(modifier = Modifier.align(Alignment.End), onClick = {
                                onEvent(ScreenEvents.NextPage)
                            }
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.ArrowForward,
                                    contentDescription = "Next Page",
                                    tint = MaterialTheme.colorScheme.onPrimaryContainer,
                                )
                            }
                        }
                }
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
fun MovieListScreenPreview() {
    MovieMadnessTheme {
        MovieListScreen(
            state = MovieListState(
                viewMode = ViewMode.GRID,
                isLoading = true,
                movies = listOf(
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
                    )
                )
            ),
            onEvent = {}
        )
    }
}