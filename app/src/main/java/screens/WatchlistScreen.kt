package screens

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.movieappmad24.models.getMovies
import simple.SimpleBottomAppBar
import simple.SimpleTopAppBar
import view.MoviesViewModel

@Composable
fun WatchlistScreen(navController: NavController, viewModel: MoviesViewModel) {

    var selectedItemId by rememberSaveable {
        mutableStateOf("WatchList")
    }

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    selectedItemId = navBackStackEntry?.destination?.route ?: "Watchlist"

    Scaffold(
        topBar = {
            SimpleTopAppBar(
                title = "Watchlist",
                navController = null,
            )
        },
        bottomBar = {
            SimpleBottomAppBar(
                currentRoute = selectedItemId,
                navController = navController
            )
        },
        content = { padding ->
            // just get a slightly different list of movies
            MovieList(movies = getMovies().drop(2).dropLast(3), padding, navController, viewModel) },
    )
}
