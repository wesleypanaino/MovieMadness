package com.wesleypanaino.moviemadness.presentation.movie_list.components

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.wesleypanaino.moviemadness.R
import com.wesleypanaino.moviemadness.domain.model.Movie
import com.wesleypanaino.moviemadness.presentation.ScreenEvents
import com.wesleypanaino.moviemadness.presentation.ScreenRoutes
import com.wesleypanaino.moviemadness.presentation.TopBarWithLogo
import com.wesleypanaino.moviemadness.presentation.ui.theme.MovieMadnessTheme

private const val TAG = "MovieListScreen"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieListScreen(
    state: MovieListState,
    onEvent: (ScreenEvents) -> Unit,
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    val context = LocalContext.current
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
                    IconButton(onClick = { Toast.makeText(context, "TV Coming soon",Toast.LENGTH_LONG).show() }) {
                        Icon(
                            painterResource(id = R.drawable.theaters_24px),
                            contentDescription = "Movies",
                            tint = MaterialTheme.colorScheme.onPrimaryContainer,
                        )
                    }
                    IconButton(onClick = { Toast.makeText(context, "Search Coming soon",Toast.LENGTH_LONG).show() }) {
                        Icon(
                            imageVector = Icons.Filled.Search,
                            contentDescription = "Search",
                            tint = MaterialTheme.colorScheme.onPrimaryContainer,
                        )
                    }
                    IconButton(onClick = { onEvent(ScreenEvents.ToggleViewMode) }) {
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
            ScreenContent(
                paddingValues = paddingValues,
                state = state,
                onEvent = onEvent
            )
    }
}

@Composable
fun ScreenContent(
    paddingValues: PaddingValues,
    state: MovieListState,
    onEvent: (ScreenEvents) -> Unit
) {
    val lazyListState = rememberLazyListState()
    val lazyGridState = rememberLazyGridState()
    Column(modifier = Modifier.padding( paddingValues)) {
        if (state.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .size(16.dp)
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
                    state = lazyListState,
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
                    item {
                        if (state.isLoading) {
                            CircularProgressIndicator(
                                modifier = Modifier.align(Alignment.CenterHorizontally)
                            )
                        }
                    }
                }
                LaunchedEffect(lazyListState) {
                    snapshotFlow { lazyListState.layoutInfo.visibleItemsInfo.lastOrNull()?.index == lazyListState.layoutInfo.totalItemsCount - 1 }
                        .collect { endReached ->
                            if (endReached && !state.isLoading) {
                                onEvent(ScreenEvents.NextPage)
                            }
                        }
                }
            }

            ViewMode.GRID -> {
                LazyVerticalGrid(
                    state = lazyGridState,
                    columns = GridCells.Adaptive(150.dp),
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
                }
                LaunchedEffect(lazyGridState) {
                    snapshotFlow { lazyGridState.layoutInfo.visibleItemsInfo.lastOrNull()?.index == lazyGridState.layoutInfo.totalItemsCount - 1 }
                        .collect { endReached ->
                            if (endReached && !state.isLoading) {
                                onEvent(ScreenEvents.NextPage)
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