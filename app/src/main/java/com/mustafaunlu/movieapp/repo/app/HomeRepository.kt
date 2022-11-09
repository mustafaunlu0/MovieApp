package com.mustafaunlu.movieapp.repo.app

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.mustafaunlu.movieapp.models.post.Post
import com.shashank.sony.fancytoastlib.FancyToast
import javax.inject.Inject

class HomeRepository @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val auth : FirebaseAuth
){
    private var postList = ArrayList<Post>()

    fun currentUser() : String{
        return auth.currentUser!!.email.toString()
    }


    fun likeMovie(userMail : String,imageUrl : String, title : String,overview : String,date : String,context : Context ){

        val likeMap= hashMapOf<String,Any>()
        likeMap["imageUrl"]=imageUrl
        likeMap["title"]=title
        likeMap["overview"]=overview
        likeMap["date"]=date

        firestore.collection("Liked-Movie").document(userMail).collection(title).add(likeMap).addOnSuccessListener {

            FancyToast.makeText(context,"Liked!",
                FancyToast.LENGTH_LONG,
                FancyToast.SUCCESS,false).show()

        }.addOnFailureListener { exception ->
            FancyToast.makeText(context,exception.localizedMessage,
                FancyToast.LENGTH_LONG,
                FancyToast.ERROR,false).show()

        }

    }

    fun postMovie(username: String,movName : String,category: String, postText : String,context: Context){
        val postMap= hashMapOf<String,Any>()
        postMap["movName"]=movName
        postMap["category"]=category
        postMap["postText"]=postText
        postMap["username"]=username

        firestore.collection("Posts").add(postMap).addOnSuccessListener {
            FancyToast.makeText(context,"Posted!",
                FancyToast.LENGTH_LONG,
                FancyToast.SUCCESS,false).show()

        }.addOnFailureListener { exception ->
            FancyToast.makeText(context,exception.localizedMessage,
                FancyToast.LENGTH_LONG,
                FancyToast.ERROR,false).show()

        }



    }

    fun findUserName(userMail: String,username: MutableLiveData<String>){

        firestore.collection("User").get().addOnSuccessListener {

            for (item in it){
                if(item.data["email"].toString()==userMail){
                    username.postValue(item.data["username"].toString())
                }
            }
        }


    }
    fun getPost(callback : GetPostList): ArrayList<Post> {

        firestore.collection("Posts").get().addOnSuccessListener {
            for (item in it){
                postList.add(Post(item.data["username"].toString(),item.data["movName"].toString(),item.data["category"].toString(),item.data["postText"].toString()))
                callback.getPostList(postList)
            }
        }
        return postList
    }

    fun getUserPhoto(userMail: String, profileImage: MutableLiveData<String>){
        firestore.collection("User").get().addOnSuccessListener {
            for (item in it){
                if(item.data["email"].toString()==userMail){
                    profileImage.postValue(item.data["downloadUri"].toString())
                }
            }
        }
    }


}