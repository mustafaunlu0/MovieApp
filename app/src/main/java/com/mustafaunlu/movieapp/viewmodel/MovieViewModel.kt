package com.mustafaunlu.movieapp.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import com.mustafaunlu.movieapp.repo.app.HomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import java.math.MathContext.UNLIMITED
import java.nio.channels.Channel
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    private var homeRepository: HomeRepository,
    private var db : FirebaseFirestore
) : ViewModel(){

    var postSize=0


    fun likeMovie(
        userMail: String,
        imageUrl: String,
        title: String,
        overview: String,
        date: String,
        context: Context){
        homeRepository.likeMovie(userMail,imageUrl, title,overview ,date,context)
    }

    fun postMovie(userMail: String,movName : String, category : String,postText : String,context: Context){
        homeRepository.postMovie(userMail, movName,category,postText, context)
    }



    fun getCurrentUserEmail() : String{
        return homeRepository.currentUser()
    }

     fun getLikedMovie(movieName: String, callback: FirebaseCallback){
                 db.collection("Liked-Movie").document(getCurrentUserEmail()).collection(movieName).get().addOnSuccessListener {
                     postSize = it.size()
                     callback.onResponse(it.size())
                     println(postSize)
                 }







    }
}