package navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import screens.DetailScreen
import screens.HomeScreen
import screens.WatchlistScreen
import view.MoviesViewModel

@Composable
fun Navigation() {
    val navController = rememberNavController()
    val viewModel: MoviesViewModel = viewModel()
    viewModel.movieList
    NavHost(navController = navController,
        startDestination = Screen.Home.route) {
        composable(route = Screen.Home.route){
            HomeScreen(navController = navController, viewModel)
        }
        composable(
            route = Screen.Detail.route,
            arguments = listOf(navArgument(name = "movieId") {
                type = NavType.StringType})
        ) { backStackEntry ->
            backStackEntry.arguments?.getString("movieId")?.let {
                DetailScreen(movieId = it, navController = navController, viewModel) }
        }
        composable(route = Screen.Watchlist.route) {
            WatchlistScreen(navController = navController, viewModel)
        }

    }
}
