package screens

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.movieappmad24.models.getMovies
import navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WatchlistScreen(navController: NavController) {

    var selectedItemId by rememberSaveable {
        mutableStateOf("WatchList")
    }

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    selectedItemId = navBackStackEntry?.destination?.route ?: "Watchlist"

    // list of items in the bottom bar
    val bottomItems = listOf(
        BottomItem("Home", "Home", Icons.Filled.Home, Icons.Outlined.Home, Screen.Home.route),
        BottomItem("WatchList", "WatchList", Icons.Filled.Star, Icons.Outlined.Star, Screen.Watchlist.route)
    )

    Scaffold(
        topBar = { CenterAlignedTopAppBar(
            title = { Text(text = "Watchlist") })
                 },
        bottomBar = {
            BottomBarW(bottomItems, selectedItemId, navController)
        },
        content = { padding -> MovieList(movies = getMovies().drop(2).dropLast(3), padding, navController) },
    )
}

@Composable
fun BottomBarW(
    bottomItems: List<BottomItem>,
    currentRoute: String,
    navController: NavController
) {
    NavigationBar {
        // loop through bottom items to create navigation items
        bottomItems.forEach { item ->
            NavigationBarItem(
                selected = item.route == currentRoute,
                onClick = { navController.navigate(route = item.route) {
                    launchSingleTop = true
                    popUpTo(navController.graph.startDestinationId) {
                        saveState = true
                    }
                    restoreState = true
                } },
                label = { Text(text = item.title) },
                icon = {
                    Icon(
                        imageVector =
                        when (item.route) {
                            currentRoute -> item.selectedIcon
                            else -> item.unselectedIcon
                        },
                        contentDescription = item.title
                    )
                }
            )
        }
    }
}