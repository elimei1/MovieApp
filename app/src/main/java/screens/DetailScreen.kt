package screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.movieappmad24.models.Movie
import com.example.movieappmad24.models.getMovies
import simple.SimpleTopAppBar


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun DetailScreen(movieId: String?, navController: NavController) {

    val movie = getMovies().find { it.id == movieId }
    Scaffold(
        topBar = {
            if (movie != null) {
                SimpleTopAppBar (
                    title = movie.title,
                    navController = navController
                ) {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            }
        },
        content = {padding ->
            movie?.let {
                MovieDetailsContent(movie = it, padding = padding)
            }
        }
    )
}

@Composable
fun MovieDetailsContent(movie: Movie, padding: PaddingValues) {
    Column(modifier = Modifier.padding(padding)) {
        MovieRow(movie = movie)

        // scrollable row
        LazyRow(
            modifier = Modifier
                .padding(padding)) {
            items(items = movie.images.drop(1)) { imageUrl ->
                Card(
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                        .size(height = 250.dp, width = 400.dp)
                ) {
                    AsyncImage(
                        model = imageUrl,
                        contentDescription = "Movie Images",
                        contentScale = ContentScale.FillBounds
                    )
                }
            }
        }
    }
}
