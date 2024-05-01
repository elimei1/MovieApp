package screens

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import di.InjectorUtils
import simple.SimpleBottomAppBar
import simple.SimpleTopAppBar
import view.HomeViewModel
import view.WatchlistViewModel

@Composable
fun WatchlistScreen(navController: NavController) {

    val watchlistViewModel: WatchlistViewModel = viewModel(factory = InjectorUtils.provideMovieViewModelFactory(context = LocalContext.current))
    val homeViewModel: HomeViewModel = viewModel(factory = InjectorUtils.provideMovieViewModelFactory(context = LocalContext.current))

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
            // use favorite function
            MovieList(watchlistViewModel.favouriteMovies.collectAsState().value, padding, navController, homeViewModel) },
    )
}
