package di

import android.content.Context
import data.MovieDatabase
import data.MovieRepository
import view.MoviesViewModelFactory

object InjectorUtils {
    private fun getMovieRepository(context: Context): MovieRepository {
        return MovieRepository.getInstance(MovieDatabase.getDatabase(context.applicationContext).movieDao())
    }
    fun provideMovieViewModelFactory(context: Context): MoviesViewModelFactory {
        val repository = getMovieRepository(context)

        return MoviesViewModelFactory(repository)
    }
}