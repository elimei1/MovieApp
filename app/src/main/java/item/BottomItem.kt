package item

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Star
import androidx.compose.ui.graphics.vector.ImageVector
import navigation.Screen

// represent bottom items in own class
data class BottomItem(
    val id: String,
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    var route: String
)

fun getBottomItems(): List<BottomItem> {
    return listOf(
        BottomItem("Home",
            "Home",
            Icons.Filled.Home,
            Icons.Outlined.Home,
            Screen.Home.route),
        BottomItem(
            "WatchList",
            "WatchList",
            Icons.Filled.Star,
            Icons.Outlined.Star,
            Screen.Watchlist.route
        )
    )
}