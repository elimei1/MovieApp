package simple

import navigation.BottomItem
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import navigation.getBottomItems

@Composable
fun SimpleBottomAppBar(
    navController: NavController,
    currentRoute: String,
) {
    NavigationBar {
        getBottomItems().forEach { item ->
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
                        if (isSelected) item.selectedIcon
                        else item.unselectedIcon,

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
