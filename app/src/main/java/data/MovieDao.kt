package data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.movieappmad24.models.Movie
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {

    @Insert
    fun insert(movie: Movie)

    @Update
    fun update(movie: Movie)

    @Delete
    fun delete(movie: Movie)

    @Query("SELECT * FROM movie")
    fun getAll(): Flow<List<Movie>>

    @Query("SELECT * FROM movie WHERE dbId = :dbId")
    fun getMovieById(dbId: Long): Flow<Movie?>

    @Query("SELECT * FROM movie WHERE isFavoriteMovie = 1")
    fun getAllMarkedAsFavorite(isFavoriteMovie: Boolean): Flow<List<Movie>>
}