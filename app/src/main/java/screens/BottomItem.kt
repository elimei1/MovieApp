package screens

import androidx.compose.ui.graphics.vector.ImageVector

// represent bottom items in own class
data class BottomItem(
    val id: String,
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val route: String
)
