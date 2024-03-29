package navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.movieappmad24.models.Movie
import com.example.movieappmad24.models.getMovies
import screens.DetailScreen
import screens.HomeScreen

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController,
        startDestination = "homescreen") {
        composable(route = "homescreen"){
            HomeScreen(navController = navController)
        }
        composable(
            route = "detailscreen/{movieId}",
            arguments = listOf(navArgument(name = "movieId") {type = NavType.StringType})
        ) { backStackEntry ->
            backStackEntry.arguments?.getString("movieId")?.let { DetailScreen(movieId = it, navController = navController) }
        }

    }
}
