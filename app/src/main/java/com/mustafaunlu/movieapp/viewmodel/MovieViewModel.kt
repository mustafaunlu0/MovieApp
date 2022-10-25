package com.mustafaunlu.movieapp.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import com.mustafaunlu.movieapp.repo.app.HomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    private var homeRepository: HomeRepository
) : ViewModel(){


    fun likeMovie(
        userMail: String,
        imageUrl: String,
        title: String,
        overview: String,
        date: String,
        context: Context){
        homeRepository.likeMovie(userMail,imageUrl, title,overview ,date,context)
    }



    fun getCurrentUserEmail() : String{
        return homeRepository.currentUser()
    }
}