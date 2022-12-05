package com.mustafaunlu.movieapp.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.mustafaunlu.movieapp.models.post.Comment
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
    var profileImage : MutableLiveData<String> = MutableLiveData()
    var commentList : MutableLiveData<ArrayList<Comment>> = MutableLiveData()
    var selectedPostList : MutableLiveData<ArrayList<Post>> = MutableLiveData()

    fun getUsername(): MutableLiveData<String> {
        return username
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

    fun postMovie(username: String,movName : String, category : String,postText : String,context: Context){
        homeRepository.postMovie(username, movName,category,postText, context)
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
    fun getSelectedPost(username: String){
        homeRepository.getSelectedPost(username,selectedPostList)
    }


    fun findUserName(userMail: String){
        println("MovieViewModel->findUserName()")
        println("model-> $userMail")
        homeRepository.findUserName(userMail,username)
    }
    fun getUserPhotoWithUsername(username: String){
        homeRepository.getUserPhotoWithUsername(username,profileImage)
    }
    fun getComments(postId : String){
        homeRepository.getComments(postId,commentList)
    }
    fun addComment(postId : String,username: String,comment : String,context: Context){
        homeRepository.addComment(postId,username,comment,context)
    }




}