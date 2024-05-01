package screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import androidx.navigation.NavController
import coil.compose.AsyncImage
import di.InjectorUtils
import models.MovieWithImages
import simple.SimpleTopAppBar
import view.DetailViewModel


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun DetailScreen(movieId: String, navController: NavController) {

    val detailViewModel: DetailViewModel = viewModel(factory = InjectorUtils.provideMovieViewModelFactory(context = LocalContext.current))
    val instance = detailViewModel.getMovieById(movieId)

    Scaffold(
        topBar = {
            if (instance != null) {
                SimpleTopAppBar (
                    title = instance.movie.title,
                    navController = navController
                )
            }
        },
        content = {padding ->
            if (instance != null) {
                MovieDetailsContent(instance = instance , padding = padding, detailViewModel = detailViewModel)
            }
        }
    )
}

@Composable
fun MovieDetailsContent(instance: MovieWithImages, padding: PaddingValues, detailViewModel: DetailViewModel) {
    LazyColumn(modifier = Modifier.padding(padding)) {
        item() {
            MovieRow(
                instance = instance,
                onFavClick = {
                    detailViewModel.toggleIsFavorite(instance)
                    detailViewModel.addOrRemove(instance)
                }
            )

            Text(
                text = "${instance.movie.title} Trailer",
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentWidth(Alignment.CenterHorizontally)
                    .padding(vertical = 8.dp))

            
            Player(movieTrailer = instance.movie.trailer)

            // scrollable row
            LazyRow(
                modifier = Modifier
            ) {
                items(items = instance.movieImages.drop(1)) { imageUrl ->
                    Card(
                        modifier = Modifier
                            .padding(horizontal = 8.dp, vertical = 30.dp)
                            .size(height = 225.dp, width = 350.dp)  // just fits this way
                    ) {
                        AsyncImage(
                            model = imageUrl,
                            contentDescription = "Movie Images",
                            contentScale = ContentScale.FillBounds
                        )
                    }
                }
            }
        }
    }
}

// from Exercise 04 Resources: https://www.youtube.com/watch?v=NpCSzl74ciY&t=506s
@Composable
fun Player(
    movieTrailer: String,
) {
    var lifecycle by remember {
        mutableStateOf(value = Lifecycle.Event.ON_CREATE)
    }
    val context = LocalContext.current

    val mediaItem =
        MediaItem.fromUri(
            "android.resource://${context.packageName}/$movieTrailer"
        )

    val exoPlayer = remember {
        ExoPlayer.Builder(context).build().apply {
            setMediaItem(mediaItem)
            prepare()
            playWhenReady = true
        }
    }

    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(key1 = lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            lifecycle = event
        }
        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            exoPlayer.release()
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    AndroidView(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(ratio = 16f / 9f),
        factory = {
            PlayerView(context).also { playerView ->
                playerView.player = exoPlayer
            }
        },
        update = {
            when (lifecycle) {
                Lifecycle.Event.ON_RESUME -> {
                    it.onPause()
                    it.player?.pause()
                }
                Lifecycle.Event.ON_STOP -> {
                    it.onResume()
                }
                else -> Unit
            }
        }
    )
}