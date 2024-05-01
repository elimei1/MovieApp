package data

import com.example.movieappmad24.models.Movie
import kotlinx.coroutines.flow.Flow
import models.MovieWithImages

class MovieRepository(private val movieDao: MovieDao) {
    suspend fun addMovie(movie: Movie) = movieDao.insert(movie)
    suspend fun updateMovie(movie: Movie) = movieDao.update(movie)
    suspend fun deleteMovie(movie: Movie) = movieDao.delete(movie)
    fun getAllMovies(): Flow<List<MovieWithImages>> = movieDao.getAll()
    fun getFavoriteMovies(): Flow<List<MovieWithImages>> = movieDao.getAllMarkedAsFavorite()
    fun getById(id: String): MovieWithImages? = movieDao.getMovieById(id)

    companion object {
        @Volatile
        private var Instance: MovieRepository? = null

        fun getInstance(movieDao: MovieDao) = Instance ?: synchronized(this) {
            Instance ?: MovieRepository(movieDao).also {
                Instance = it
            }
        }
    }

}