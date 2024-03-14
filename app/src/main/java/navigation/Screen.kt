package navigation

sealed class Screen(val route: String) {
    object Home: Screen(route = "homescreen")
    object Detail: Screen(route = "detailscreen") {
        fun passMovieId(movieId: String) = "detailscreen/$movieId"
    }
}