package navigation

const val MOVIE_ID = "movieId"

sealed class Screen(val route: String) {
    object Home: Screen(route = "homescreen")
    object Detail: Screen(route = "detailscreen/{movieId}") {
        fun passMovieId(movieId: String): String {
            return "detailscreen/$movieId"
        }
    }
    object Watchlist: Screen(route = "watchlistscreen")
}