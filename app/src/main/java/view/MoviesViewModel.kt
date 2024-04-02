package view

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import com.example.movieappmad24.models.Movie
import com.example.movieappmad24.models.getMovies

class MoviesViewModel: ViewModel() {
    private val _movieList = getMovies().toMutableStateList()
    val movieList: List<Movie>
        get() = _movieList

    private val _favoriteMovieList = mutableStateListOf<Movie>()
    val favoriteMovieList: List<Movie>
        get() = _favoriteMovieList

    fun toggleIsFavorite(movie : Movie) {
        movie.isFavoriteMovie = !movie.isFavoriteMovie
    }

    fun addOrRemoveFromFavorite(movie: Movie) {
        if (!movie.isFavoriteMovie) {
            _favoriteMovieList.add(movie)
        } else {
            _favoriteMovieList.remove(movie)
        }
    }
}