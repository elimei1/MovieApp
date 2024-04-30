package view

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieappmad24.models.Movie
import com.example.movieappmad24.models.getMovies
import data.MovieRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

class MoviesViewModel(private val repository: MovieRepository): ViewModel() {

    private val _movies = MutableStateFlow(listOf<Movie>())
    val movieList: StateFlow<List<Movie>> = _movies.asStateFlow()


    init {
        viewModelScope.launch {
            repository.getAllMovies().distinctUntilChanged()
                .collect{ listOfMovies ->
                    _movies.value = listOfMovies
                }
        }
    }

/*
    private val _movieList = getMovies().toMutableStateList()
    val movieList: List<Movie>
        get() = _movieList

    private val _favoriteMovieList = mutableStateListOf<Movie>()
    val favoriteMovieList: List<Movie>
        get() = _favoriteMovieList

    fun toggleIsFavorite(movie : Movie) {
        movie.isFavoriteMovie = !movie.isFavoriteMovie
    }

    fun addOrRemove(movie: Movie) {
        if (movie.isFavoriteMovie) {
            _favoriteMovieList.add(movie)
        } else {
            _favoriteMovieList.remove(movie)
        }
    }
 */

}