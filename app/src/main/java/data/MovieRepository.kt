package data

import com.example.movieappmad24.models.Movie
import kotlinx.coroutines.flow.Flow

class MovieRepository(private val movieDao: MovieDao) {
    suspend fun addMovie(movie: Movie) = movieDao.insert(movie)
    suspend fun updateMovie(movie: Movie) = movieDao.update(movie)
    suspend fun deleteMovie(movie: Movie) = movieDao.delete(movie)
    fun getAllMovies(): Flow<List<Movie>> = movieDao.getAll()
    fun getFavoriteMovies(): Flow<List<Movie>> = movieDao.getAllMarkedAsFavorite()
    fun getById(id: Long): Flow<Movie?> = movieDao.getMovieById(id)

}