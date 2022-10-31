package com.mustafaunlu.movieapp.repo.retrofit

import com.mustafaunlu.movieapp.models.api.Genre
import com.mustafaunlu.movieapp.models.api.Movie
import com.mustafaunlu.movieapp.util.Constants
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface RetrofitServiceInstance {

    //İLK İKİ QUERY İÇİN GERİ DON
    @GET("3/movie/popular?api_key=${Constants.API_KEY}")
    fun getPopularMovies(@Query("page") query : String) : Call<Movie>

    @GET("3/movie/now_playing?api_key=${Constants.API_KEY}")
    fun getRecentMovies(@Query("page") query : String) : Call<Movie>

    @GET("3/genre/movie/list?api_key=${Constants.API_KEY}")
    fun getGenres() : Call<Genre>




}