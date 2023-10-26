package com.wesleypanaino.moviemadness.presentation.movie_details.components

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wesleypanaino.moviemadness.common.Resource
import com.wesleypanaino.moviemadness.domain.use_case.get_movie.GetMovieDetailUseCase
import com.wesleypanaino.moviemadness.presentation.ScreenEvents
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val getMovieDetailUseCase: GetMovieDetailUseCase,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val TAG = "MovieDetailViewModel"
    private val _state = mutableStateOf(MovieDetailState())
    val state: State<MovieDetailState> = _state

    private val movieId: String =
        savedStateHandle["movieId"] ?: throw IllegalStateException("Missing movieId")

    init {
        getMovieDetail(movieId)
    }

    private fun getMovieDetail(movieId: String) {
        viewModelScope.launch {
            try {
                getMovieDetailUseCase(movieId)
                    .collect { result ->  // Use collect instead of onEach
                        when (result) {
                            is Resource.Success -> {
                                _state.value = MovieDetailState(movieDetail = result.data)
                            }

                            is Resource.Error -> {
                                _state.value = MovieDetailState(
                                    error = result.message ?: "An unexpected error occurred"
                                )
                            }

                            is Resource.Loading -> {
                                _state.value = MovieDetailState(isLoading = true)
                            }
                        }
                    }
            } catch (e: Exception) {
                _state.value =
                    MovieDetailState(error = e.localizedMessage ?: "An unexpected error occurred")
            }
        }
    }

    fun onEvent(screenEvents: ScreenEvents) {
        when (screenEvents) {
            is ScreenEvents.Navigate -> {
                Log.e(TAG, "MovieDetailsViewModel.ScreenEvents.Navigate")
                //not handled here
            }

            is ScreenEvents.ShowSnackBar -> {
                Log.e(TAG, "MovieDetailsViewModel.ScreenEvents.ShowSnackBar")
                //not handled here
            }

            is ScreenEvents.Refresh -> {
                // no need for refresh
            }

            ScreenEvents.NextPage -> {
                // no need for refresh
            }

            ScreenEvents.PreviousPage -> {
                // no need for refresh
            }

            ScreenEvents.GoBack -> {
                Log.e(TAG, "MovieListViewModel.ScreenEvents.GoBack")
            }

            ScreenEvents.ToggleViewMode -> {
                //not used here
            }
        }
    }
}