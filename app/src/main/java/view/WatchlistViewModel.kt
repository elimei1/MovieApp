package view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import data.MovieRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import models.MovieWithImages
import kotlinx.coroutines.flow.distinctUntilChanged

class WatchlistViewModel(private val repository: MovieRepository) : ViewModel() {

    private val _favouriteMovies = MutableStateFlow(listOf<MovieWithImages>())
    val favouriteMovies: StateFlow<List<MovieWithImages>> = _favouriteMovies.asStateFlow()

    fun addOrRemove(instance: MovieWithImages) {
        if (instance.movie.isFavoriteMovie) {
            _favouriteMovies.value += instance
        } else {
            _favouriteMovies.value -= instance
        }
    }

    fun toggleIsFavorite(instance: MovieWithImages) {
        instance.movie.isFavoriteMovie = !instance.movie.isFavoriteMovie
        viewModelScope.launch {
            repository.updateMovie(instance.movie)
        }
    }

    init {
        viewModelScope.launch {
            repository.getFavoriteMovies().distinctUntilChanged().collect { movies ->
                _favouriteMovies.value = movies
            }
        }
    }
}