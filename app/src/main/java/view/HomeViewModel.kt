package view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieappmad24.models.Movie
import data.MovieRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import models.MovieWithImages

class HomeViewModel(private val repository: MovieRepository) : ViewModel() {

    private val _movies = MutableStateFlow(listOf<MovieWithImages>())
    val movieList: StateFlow<List<MovieWithImages>> = _movies.asStateFlow()

    fun toggleIsFavorite(instance: MovieWithImages) =
        WatchlistViewModel(repository).addOrRemove(instance)

    fun addOrRemove(instance: MovieWithImages) =
        WatchlistViewModel(repository).toggleIsFavorite(instance)

    init {
        viewModelScope.launch {
            repository.getAllMovies().distinctUntilChanged()
                .collect{ movieList ->
                    _movies.value = movieList
                }
        }
    }

}