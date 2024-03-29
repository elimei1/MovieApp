package simple

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import bottomItem.bottomItems

@Composable
fun SimpleBottomAppBar(
    navController: NavController,
    currentRoute: String,
) {
    NavigationBar {
        bottomItems.forEach { item ->
            val isSelected = item.route == currentRoute
            NavigationBarItem(
                selected = isSelected,
                onClick = {
                    navController.navigate(item.route) {
                        launchSingleTop = true
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                        restoreState = true                    }
                },
                icon = {
                    Icon(
                        imageVector =
                        if (!isSelected) item.unselectedIcon
                        else item.selectedIcon,

                        contentDescription = item.title
                    )
                },
                label = {
                    Text(
                        text = item.title
                    )
                }
            )
        }
    }
}
