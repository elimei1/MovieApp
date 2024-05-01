package view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import data.MovieRepository
import kotlinx.coroutines.launch
import models.MovieWithImages


class DetailViewModel(private val repository: MovieRepository) : ViewModel() {

    fun getMovieById(movieId: String): MovieWithImages? {
        var movie: MovieWithImages? = null

        viewModelScope.launch {
            movie = repository.getById(movieId)
        }
        return movie
    }

    fun toggleIsFavorite(instance: MovieWithImages) = WatchlistViewModel(repository).addOrRemove(instance)

    fun addOrRemove(instance: MovieWithImages) = WatchlistViewModel(repository).toggleIsFavorite(instance)
}

