package view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import data.MovieRepository

class MoviesViewModelFactory(private val repository: MovieRepository): ViewModelProvider.Factory {
    override fun<T: ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(HomeViewModel::class.java)){
            return HomeViewModel(repository) as T
        }
        if(modelClass.isAssignableFrom(WatchlistViewModel::class.java)){
            return WatchlistViewModel(repository) as T
        }
        if(modelClass.isAssignableFrom(DetailViewModel::class.java)){
            return DetailViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}