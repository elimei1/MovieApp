package navigation

const val MOVIE_ID = "movieId"

sealed class Screen(val route: String) {
    object Home: Screen(route = "homescreen")
    object Detail: Screen(route = "detailscreen/{$MOVIE_ID}") {
        fun passMovieId(movieId: String): String {
            return this.route.replace(oldValue = "{$MOVIE_ID}", newValue = movieId)
        }
    }
    object Watchlist: Screen(route = "watchlistscreen")
}