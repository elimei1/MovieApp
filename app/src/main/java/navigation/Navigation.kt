package navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import screens.DetailScreen
import screens.HomeScreen
import screens.WatchlistScreen

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController,
        startDestination = Screen.Home.route) {
        composable(route = Screen.Home.route){
            HomeScreen(navController = navController)
        }
        composable(
            route = Screen.Detail.route,
            arguments = listOf(navArgument(MOVIE_ID) {
                type = NavType.StringType})
        ) { backStackEntry ->
            backStackEntry.arguments?.getString("movieId")?.let {
                DetailScreen(movieId = it, navController = navController) }
        }
        composable(route = Screen.Watchlist.route) {
            WatchlistScreen(navController = navController)
        }

    }
}
