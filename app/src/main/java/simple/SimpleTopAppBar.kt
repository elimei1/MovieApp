package simple

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.movieappmad24.ui.theme.Purple80

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SimpleTopAppBar(
    title: String,
    navController: NavController? = null,
    navigationIcon: @Composable (() -> Unit)? = null
) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = title
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Purple80
        ),
        navigationIcon = {
            navigationIcon ?:
            if (navController != null) {
                run {
                    IconButton(
                        onClick = { navController.navigateUp() })
                    {
                        Icon(
                            Icons.Filled.ArrowBack,
                            contentDescription = "Back")
                    }
                }
            } else {
                null
            }
        }
    )
}
