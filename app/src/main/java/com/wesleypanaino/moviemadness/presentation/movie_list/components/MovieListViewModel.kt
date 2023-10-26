package com.wesleypanaino.moviemadness.presentation.movie_list.components

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wesleypanaino.moviemadness.common.Resource
import com.wesleypanaino.moviemadness.domain.use_case.get_movies.GetMovieUseCase
import com.wesleypanaino.moviemadness.presentation.ScreenEvents
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieListViewModel @Inject constructor(
    private val getMovieUseCase: GetMovieUseCase
) : ViewModel() {
    var viewMode: ViewMode = ViewMode.GRID
    private val _state = mutableStateOf(MovieListState(viewMode = viewMode))
    val state: State<MovieListState> = _state
    private val TAG = "MovieListViewModel"
    private var currentPage = 1

    init {
        getMovies()
    }
    //todo boundary checks
    private fun getPreviousPage() {
        if (currentPage > 1) {
            currentPage--
            getMovies()
        }
    }

    private fun getNextPage() {
        currentPage++
        getMovies()
    }

    private fun getMovies() {
        viewModelScope.launch {
            try {
                getMovieUseCase(currentPage)
                    .collect { result ->
                        when (result) {
                            is Resource.Success -> {
                                _state.value = MovieListState(viewMode= viewMode,
                                    movies = result.data ?: emptyList())
                            }

                            is Resource.Error -> {
                                _state.value = MovieListState(viewMode= viewMode,
                                    error = result.message ?: "An unexpected error occurred"
                                )
                            }

                            is Resource.Loading -> {
                                _state.value = MovieListState(viewMode= viewMode,isLoading = true)
                            }
                        }
                    }
            } catch (e: Exception) {
                _state.value =
                    MovieListState(viewMode=viewMode,error = e.localizedMessage ?: "An unexpected error occurred")
            }
        }
    }

    fun onEvent(screenEvents: ScreenEvents) {
        when (screenEvents) {
            is ScreenEvents.Navigate -> {
                Log.e(TAG, "MovieListViewModel.ScreenEvents.Navigate")
                //not handled here
            }

            is ScreenEvents.ShowSnackBar -> {
                Log.e(TAG, "MovieListViewModel.ScreenEvents.ShowSnackBar")
                //not handled here
            }

            is ScreenEvents.Refresh -> {
                getMovies()
            }

            ScreenEvents.NextPage -> {
                getNextPage()
            }

            ScreenEvents.PreviousPage -> {
                getPreviousPage()
            }

            ScreenEvents.GoBack -> {}
            ScreenEvents.ToggleViewMode -> {
                viewMode = viewMode.next()
                Log.d(TAG, "viewMode changed to: $viewMode")
                _state.value = MovieListState(viewMode = viewMode, movies = _state.value.movies)
            }
        }
    }
}