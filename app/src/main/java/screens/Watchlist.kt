package screens

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Star
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
import com.example.movieappmad24.models.getMovies

@Composable
fun Watchlist(navController: NavController) {

    var selectedItemId by rememberSaveable {
        mutableStateOf("Home")
    }
    // list of items in the bottom bar
    val bottomItems = listOf(
        BottomItem("Home", "Home", Icons.Filled.Home, Icons.Outlined.Home),
        BottomItem("WatchList", "WatchList", Icons.Filled.Star, Icons.Outlined.Star)
    )

    Scaffold(
        topBar = { TopBar() },
        bottomBar = {
            BottomBarW(bottomItems, selectedItemId) { itemId ->
                selectedItemId = itemId // update itemId if selected
            }
        },
        content = { padding -> MovieList(movies = getMovies(), padding, navController) },
    )
}

@Composable
fun BottomBarW(
    bottomItems: List<BottomItem>,
    selectedItemId: String,
    onItemSelected: (String) -> Unit
) {
    NavigationBar {
        // loop through bottom items to create navigation items
        bottomItems.forEach { item ->
            NavigationBarItem(
                selected = selectedItemId == item.id,   // is item selected?
                onClick = { onItemSelected(item.id) },  // select when clicked
                label = { Text(text = item.title) },
                icon = {
                    Icon(
                        imageVector =
                        if (selectedItemId == item.id) item.selectedIcon
                        else item.unselectedIcon,
                        contentDescription = item.title
                    )
                }
            )
        }
    }
}