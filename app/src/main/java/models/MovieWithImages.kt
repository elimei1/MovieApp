package models

import androidx.room.Embedded
import androidx.room.Relation

data class MovieWithImages(
    @Embedded
    val movie: Movie,
    @Relation(
        parentColumn = "movieId",
        entityColumn = "movieId"
    )
    val movieImages: List<MovieImage>
)