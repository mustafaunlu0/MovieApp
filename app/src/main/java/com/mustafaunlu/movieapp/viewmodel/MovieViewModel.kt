package com.mustafaunlu.movieapp.viewmodel

import android.content.Context
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.mustafaunlu.movieapp.models.post.Comment
import com.mustafaunlu.movieapp.models.post.LikedMovie
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

    private var username : MutableLiveData<String> = MutableLiveData()
    var profileImage : MutableLiveData<String> = MutableLiveData()
    var commentList : MutableLiveData<ArrayList<Comment>> = MutableLiveData()
    var selectedPostList : MutableLiveData<ArrayList<Post>> = MutableLiveData()
    var postList : MutableLiveData<ArrayList<Post>> = MutableLiveData()
    var likedMovieList : MutableLiveData<ArrayList<LikedMovie>> = MutableLiveData()

    fun getUsername(): MutableLiveData<String> {
        return username
    }

    fun getUserLikedMovie(){
        homeRepository.getUserLikedMovie(likedMovieList)
    }


    fun likeMovie(
        imageUrl: String,
        title: String,
        overview:String,
        date: String,
        context: Context){
        homeRepository.likeMovie(title,overview,date,imageUrl,context)
    }

    fun setPostList(postedList : ArrayList<Post>){
        postList.postValue(postedList)
    }

    fun filterList(text: String?, postedList : ArrayList<Post>) : ArrayList<Post>{
        var itemList : ArrayList<Post> = arrayListOf()
        for(item in postedList){
            if(item.post.toString().toLowerCase().contains(text?.toLowerCase().toString())){
                itemList.add(item)
            }
        }
        return itemList
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun postMovie(username: String, movName : String, category : String, postText : String, context: Context){
        homeRepository.postMovie(username, movName,category,postText, context)
    }


    fun getCurrentUserEmail() : String{
        return homeRepository.currentUser()
    }

    fun getUserPhoto(userMail: String){
        homeRepository.getUserPhoto(userMail,profileImage)
    }

     fun getLikedMovie(movieName: String, callback: FirebaseCallback){
         homeRepository.getLikedMovie(movieName,callback)

    }
    fun getPost(callback : GetPostList): ArrayList<Post> {
        return homeRepository.getPost(callback)
    }
    fun getSelectedPost(username: String){
        homeRepository.getSelectedPost(username,selectedPostList)
    }
    fun findUserName(userMail: String){

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
    fun logout(){
        homeRepository.logout()
    }




}