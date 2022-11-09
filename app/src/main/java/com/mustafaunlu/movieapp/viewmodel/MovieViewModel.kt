package com.mustafaunlu.movieapp.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.mustafaunlu.movieapp.models.post.Post
import com.mustafaunlu.movieapp.repo.app.GetPostList
import com.mustafaunlu.movieapp.repo.app.HomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    private var homeRepository: HomeRepository,
    private var db : FirebaseFirestore
) : ViewModel(){

    var postSize=0
    private var username : MutableLiveData<String> = MutableLiveData()
    private var profileImage : MutableLiveData<String> = MutableLiveData()

    fun getUsername(): MutableLiveData<String> {
        return username
    }
    fun getProfileImage(): MutableLiveData<String> {
        return profileImage
    }

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

    fun getUserPhoto(userMail: String){
        homeRepository.getUserPhoto(userMail,profileImage)
    }

     fun getLikedMovie(movieName: String, callback: FirebaseCallback){
                 db.collection("Liked-Movie").document(getCurrentUserEmail()).collection(movieName).get().addOnSuccessListener {
                     postSize = it.size()
                     callback.onResponse(it.size())
                     println(postSize)
                 }

    }
    fun getPost(callback : GetPostList): ArrayList<Post> {
        return homeRepository.getPost(callback)
    }

    fun findUserName(userMail: String){
        homeRepository.findUserName(userMail,username)
    }


}