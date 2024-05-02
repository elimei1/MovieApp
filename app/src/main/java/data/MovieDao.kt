package data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import models.Movie
import kotlinx.coroutines.flow.Flow
import models.MovieWithImages

@Dao
interface MovieDao {

    @Insert
    suspend fun insert(movie: Movie)

    @Update
    suspend fun update(movie: Movie)

    @Delete
    suspend fun delete(movie: Movie)

    @Query("SELECT * FROM movie")
    fun getAll(): Flow<List<MovieWithImages>>

    @Query("SELECT * FROM movie WHERE dbId = :dbId")
    fun getMovieById(dbId: String): MovieWithImages?

    @Query("SELECT * FROM movie WHERE isFavoriteMovie = 1")
    fun getAllMarkedAsFavorite(): Flow<List<MovieWithImages>>
}