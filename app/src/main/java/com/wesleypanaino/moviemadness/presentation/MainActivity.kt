package com.wesleypanaino.moviemadness.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.wesleypanaino.moviemadness.common.Constants
import com.wesleypanaino.moviemadness.presentation.movie_details.components.MovieDetailScreen
import com.wesleypanaino.moviemadness.presentation.movie_details.components.MovieDetailViewModel
import com.wesleypanaino.moviemadness.presentation.movie_list.components.MovieListScreen
import com.wesleypanaino.moviemadness.presentation.movie_list.components.MovieListViewModel
import com.wesleypanaino.moviemadness.presentation.ui.theme.MovieMadnessTheme
import dagger.hilt.android.AndroidEntryPoint

private val TAG = "MainActivity"

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MovieMadnessTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = ScreenRoutes.MovieListScreen.route
                    ) {
                        composable(ScreenRoutes.MovieListScreen.route) {
                            val viewModel: MovieListViewModel = hiltViewModel()
                            val state by viewModel.state
                            MovieListScreen(state = state,
                                onEvent = { event ->
                                    handleEvent(event, navController, viewModel::onEvent)
                                }
                            )
                        }
                        composable(route = ScreenRoutes.MovieDetailScreen.route + "/{${Constants.PARAM_MOVIE_ID}}") {
                            val viewModel: MovieDetailViewModel = hiltViewModel()
                            val state by viewModel.state
                            MovieDetailScreen(state = state, onEvent = { event ->
                                handleEvent(event, navController, viewModel::onEvent)
                            })
                        }
                    }
                }
            }
        }
    }
}

private fun handleEvent(
    event: ScreenEvents,
    navController: NavController,
    viewModelEvent: (ScreenEvents) -> Unit
) {
    when (event) {
        is ScreenEvents.Navigate -> {
            navController.navigate(event.route)
        }

        is ScreenEvents.ShowSnackBar -> {
            //todo
        }

        is ScreenEvents.GoBack -> {
           if(!navController.popBackStack()){
               navController.navigate( ScreenRoutes.MovieListScreen.route)
           }
        }

        else -> viewModelEvent(event)
    }
}