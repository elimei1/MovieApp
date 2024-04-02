package screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.movieappmad24.models.Movie
import com.example.movieappmad24.models.getMovies
import navigation.Screen
import simple.SimpleTopAppBar
import view.MoviesViewModel


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun DetailScreen(movieId: String?, navController: NavController, viewModel: MoviesViewModel) {

    val movie = getMovies().find { it.id == movieId }

    Scaffold(
        topBar = {
            if (movie != null) {
                SimpleTopAppBar (
                    title = movie.title,
                    navController = navController
                )
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
    LazyColumn(modifier = Modifier.padding(padding)) {
        item() {
            MovieRow(movie = movie)

            // scrollable row
            LazyRow(
                modifier = Modifier
            ) {
                items(items = movie.images.drop(1)) { imageUrl ->
                    Card(
                        modifier = Modifier
                            .padding(horizontal = 8.dp, vertical = 20.dp)
                            .size(height = 250.dp, width = 400.dp)  // just fits this way
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
}
