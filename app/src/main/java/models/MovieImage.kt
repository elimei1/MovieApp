package models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MovieImage(
    @PrimaryKey(autoGenerate = true)
    val imageId: Long = 0,
    val movieId: Long,
    val url: String
)
