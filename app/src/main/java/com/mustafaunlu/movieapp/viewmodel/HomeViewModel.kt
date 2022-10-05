package com.mustafaunlu.movieapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mustafaunlu.movieapp.models.api.Genre
import com.mustafaunlu.movieapp.models.api.Movie
import com.mustafaunlu.movieapp.repo.retrofit.RetrofitRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: RetrofitRepository) : ViewModel() {

    private var popularMovieList : MutableLiveData<Movie> = MutableLiveData()
    private var recentMovieList : MutableLiveData<Movie> = MutableLiveData()
    private var genreList : MutableLiveData<Genre> = MutableLiveData()



    fun getObserverRecentMovie() : MutableLiveData<Movie>{
        return recentMovieList
    }
    fun getObserverPopularMovie() : MutableLiveData<Movie>{
        return popularMovieList
    }

    fun loadRecentMovieData(page : String){
        repository.getRecentMovies(page,recentMovieList)
    }
    fun loadPopularMovieData(page : String){
        repository.getPopularMovies(page,popularMovieList)
    }

}