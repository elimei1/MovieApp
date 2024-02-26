package com.example.movieappmad24

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ComposeCompilerApi
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.movieappmad24.models.Movie
import com.example.movieappmad24.models.getMovies
import com.example.movieappmad24.ui.theme.MovieAppMAD24Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MovieAppMAD24Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MovieList(movies = getMovies())
                }
            }
        }
    }
    @Composable
    fun MovieRow(movie: Movie) {
        Card (modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp), shape = ShapeDefaults.Large){


            Column {
                Box (modifier = Modifier
                    .height(170.dp)){
                    Image(
                        painter = painterResource(id = R.drawable.movie_image),
                        contentDescription = "placeholder_image",
                        contentScale = ContentScale.FillWidth,
                        modifier = Modifier
                            .aspectRatio(ratio= 21f/9f)
                    )
                    Box (modifier = Modifier
                        .fillMaxSize()
                        .padding(10.dp),
                        contentAlignment = Alignment.TopEnd){
                        Icon(
                            imageVector = Icons.Default.FavoriteBorder,
                            contentDescription = "heart",
                            modifier = Modifier
                                .align(alignment = Alignment.TopEnd)
                        )
                    }

                }
                Row (modifier = Modifier
                    .fillMaxWidth()
                    .height(35.dp)
                    .padding(4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween){
                    Text(text = movie.title)
                    Icon(imageVector = Icons.Default.KeyboardArrowUp, contentDescription = "arrow")
                }
            }
        }
    }

    @Composable
    fun MovieList(movies: List<Movie> = getMovies()) {
        LazyColumn{
            items(movies) { movie ->
                MovieRow(movie = movie)
            }
        }
    }
}
