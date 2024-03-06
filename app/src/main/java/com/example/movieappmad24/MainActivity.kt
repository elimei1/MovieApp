package com.example.movieappmad24

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale.Companion.FillWidth
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.movieappmad24.models.Movie
import com.example.movieappmad24.models.getMovies
import com.example.movieappmad24.ui.theme.MovieAppMAD24Theme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MovieAppMAD24Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = colorScheme.background
                ) {
                    MainScreen()
                }
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun MainScreen() {
        var selectedItemIndex by rememberSaveable {
            mutableStateOf(0)
        }
        val bottomItems = listOf(
            BottomItem("Home", Icons.Filled.Home, Icons.Outlined.Home),
            BottomItem("WatchList", Icons.Filled.Star, Icons.Outlined.Star)
        )
        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(title = { Text(text = "Elias' Movie App") })
            },
            bottomBar = {
                NavigationBar {
                    bottomItems.forEachIndexed { index, item ->
                        NavigationBarItem(
                            selected = selectedItemIndex == index,
                            onClick = { selectedItemIndex = index },
                            label = {
                                Text(text = item.title)
                            },
                            icon = {
                                Icon(
                                    imageVector = if (index == selectedItemIndex) {
                                        item.selectedIcon
                                    } else {
                                        item.unselectedIcon
                                    },
                                    contentDescription = item.title
                                )
                            }
                        )
                    }
                }
            },
            content = { MovieList(movies = getMovies(), padding = it) },
        )
    }

    @Composable
    fun MovieRow(movie: Movie) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp),
            shape = RoundedCornerShape(size = 20.dp),

            ) {
            Column {
                var favorite by remember {
                    mutableStateOf(false)
                }
                Box {
                    AsyncImage(
                        model = movie.images[0],
                        contentDescription = "images",
                        contentScale = FillWidth,
                        modifier = Modifier
                            .aspectRatio(18.5f / 9f)
                    )
                    Icon(
                        imageVector = Icons.Default.FavoriteBorder,
                        contentDescription = "heart",
                        modifier = Modifier
                            .align(alignment = Alignment.TopEnd)
                            .padding(8.dp)
                            .clickable {
                                favorite = !favorite
                            },
                        tint =
                        if (favorite) Red
                        else colorScheme.secondary
                    )
                }
                var open by remember {
                    mutableStateOf(false)
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = movie.title,
                        modifier = Modifier
                            .padding(8.dp)
                            .weight(weight = 7f),
                        fontSize = 18.sp
                    )
                    Icon(
                        imageVector =
                        if (open) Icons.Default.KeyboardArrowDown
                        else Icons.Default.KeyboardArrowUp,
                        contentDescription = "arrow",
                        modifier = Modifier
                            .clickable {
                                open = !open
                            }
                            .padding(8.dp)
                    )
                }
                AnimatedVisibility(visible = open) {
                    Column(modifier = Modifier.padding(all = 12.dp)) {
                        Text(text = "Director: ${movie.director}")
                        Text(text = "Released: ${movie.year}")
                        Text(text = "Genre: ${movie.genre}")
                        Text(text = "Actors: ${movie.actors}")
                        Text(text = "Rating: ${movie.rating}")
                        Divider(color = Color.DarkGray, thickness = 1.dp)
                        Text(text = "Plot: ${movie.plot}")
                    }
                }
            }
        }
    }

    @Composable
    fun MovieList(
        movies: List<Movie> = getMovies(),
        padding: PaddingValues
    ) {
        LazyColumn(
            modifier = Modifier
                .padding(paddingValues = padding)
        ) {
            items(items = movies) { movie ->
                MovieRow(movie = movie)
            }
        }
    }
}

data class BottomItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector
)
