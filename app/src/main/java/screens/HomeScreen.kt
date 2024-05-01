package screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import coil.compose.AsyncImage
import data.MovieDatabase
import data.MovieRepository
import di.InjectorUtils
import models.MovieWithImages
import navigation.Screen
import simple.SimpleBottomAppBar
import simple.SimpleTopAppBar
import view.HomeViewModel
import view.MoviesViewModelFactory

@Composable
fun HomeScreen(navController: NavController) {

    val db = MovieDatabase.getDatabase(LocalContext.current)
    val repository = MovieRepository(movieDao = db.movieDao())
    val factory = MoviesViewModelFactory(repository = repository)
    val homeViewModel: HomeViewModel = viewModel(factory = InjectorUtils.provideMovieViewModelFactory(context = LocalContext.current))
    // state for currently selected item
    var selectedItemId by rememberSaveable {
        mutableStateOf("Home")
    }

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    selectedItemId = navBackStackEntry?.destination?.route ?: "Home"

    // scaffold including simple topbar, simple bottombar and content
    Scaffold(
        topBar = {
            SimpleTopAppBar(
                title = "Movie App",
                navController = null
            )
        },
        bottomBar = {
            SimpleBottomAppBar(
                currentRoute = selectedItemId,
                navController = navController
            )
        },
        content = { padding ->
            MovieList(homeViewModel.movieList.collectAsState().value, padding, navController, homeViewModel)
        },
    )
}

@Composable
fun MovieRow(instance: MovieWithImages, onItemClick: (String) -> Unit = {}, onFavClick: () -> Unit = {}) {
    // whole thing
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp)
            .clickable {
                onItemClick(instance.movie.id)
            },
        shape = RoundedCornerShape(size = 20.dp),
    ) {
        Column {
            Box {
                AsyncImage(
                    // first image because of list of images
                    model = instance.movieImages[0],
                    contentDescription = "images",
                    contentScale = ContentScale.FillWidth,
                    modifier = Modifier
                        .aspectRatio(18.5f / 9f)
                )
                Icon(
                    contentDescription = "heart",
                    tint = Color.Red,
                    modifier = Modifier
                        .align(alignment = Alignment.TopEnd)
                        .padding(8.dp)
                        .clickable { onFavClick() },
                    imageVector =
                    if (instance.movie.isFavoriteMovie) Icons.Default.Favorite
                    else Icons.Default.FavoriteBorder
                )

            }
            // state to check if card expanded
            var open by remember {
                mutableStateOf(false)
            }
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = instance.movie.title,
                    modifier = Modifier
                        .padding(8.dp)
                        .weight(weight = 7f),
                    fontSize = 18.sp
                )
                // icon for expanding
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
                    // multiline string
                    val movieDetails = """
                            |Director: ${instance.movie.director}
                            |Released: ${instance.movie.year}
                            |Genre: ${instance.movie.genre}
                            |Actors: ${instance.movie.actors}
                            |Rating: ${instance.movie.rating}
                        """.trimMargin()    // remove whitespaces on left side

                    Text(text = movieDetails)
                    Divider(color = Color.Black, thickness = 2.dp)
                    Text(text = "Plot: ${instance.movie.plot}")
                }
            }
        }
    }
}

@Composable
fun MovieList(
    movies: List<MovieWithImages>,
    padding: PaddingValues,
    navController: NavController,
    viewModel: HomeViewModel
) {
    LazyColumn(
        modifier = Modifier
            .padding(paddingValues = padding)
    ) {
        items(items = movies) { instance ->
            MovieRow(
                instance = instance,
                onFavClick = {
                    viewModel.toggleIsFavorite(instance)
                    viewModel.addOrRemove(instance)
                },
                onItemClick = { movieId ->
                    navController.navigate(Screen.Detail.passMovieId(movieId))
                }
            )
        }
    }
}
