package com.mustafaunlu.movieapp.repo.retrofit

import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.mustafaunlu.movieapp.models.api.Genre
import com.mustafaunlu.movieapp.models.api.Movie
import retrofit2.Call
import javax.inject.Inject
import retrofit2.Callback
import retrofit2.Response


class RetrofitRepository @Inject constructor(
    private val retrofitServiceInstance: RetrofitServiceInstance
) {

    fun getPopularMovies(page : String, liveData : MutableLiveData<Movie>){

        retrofitServiceInstance.getPopularMovies(page).enqueue(object : Callback<Movie>{
            override fun onResponse(call: Call<Movie>, response: Response<Movie>) {
                liveData.postValue(response.body())
            }

            override fun onFailure(call: Call<Movie>, t: Throwable) {
                liveData.postValue(null)
            }


        })
    }

    fun getRecentMovies(page : String, liveData: MutableLiveData<Movie>){
        retrofitServiceInstance.getRecentMovies(page).enqueue(object : Callback<Movie>{
            override fun onResponse(call: Call<Movie>, response: Response<Movie>) {
                liveData.postValue(response.body())
            }

            override fun onFailure(call: Call<Movie>, t: Throwable) {
                liveData.postValue(null)
            }

        })
    }

    fun getGenres(liveData: MutableLiveData<Genre>){
        retrofitServiceInstance.getGenres().enqueue(object  : Callback<Genre>{
            override fun onResponse(call: Call<Genre>, response: Response<Genre>) {
                liveData.postValue(response.body())


            }

            override fun onFailure(call: Call<Genre>, t: Throwable) {
                liveData.postValue(null)

            }

        })
    }


}

