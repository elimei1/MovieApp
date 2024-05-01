package models

import androidx.room.Embedded
import androidx.room.Relation
import com.example.movieappmad24.models.Movie

data class MovieWithImages(
    @Embedded
    val movie: Movie,
    @Relation(
        parentColumn = "movieId",
        entityColumn = "movieId"
    )
    val movieImages: List<MovieImage>
)