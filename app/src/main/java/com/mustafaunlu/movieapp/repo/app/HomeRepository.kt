package com.mustafaunlu.movieapp.repo.app

import android.content.Context
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.shashank.sony.fancytoastlib.FancyToast
import javax.inject.Inject

class HomeRepository @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val auth : FirebaseAuth
){

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

    fun postMovie(userMail: String,movName : String,category: String, postText : String,context: Context){
        val postMap= hashMapOf<String,Any>()
        postMap["movName"]=movName
        postMap["category"]=category
        postMap["postText"]=postText

        firestore.collection("Posts").document(userMail).collection(movName).add(postMap).addOnSuccessListener {
            FancyToast.makeText(context,"Posted!",
                FancyToast.LENGTH_LONG,
                FancyToast.SUCCESS,false).show()

        }.addOnFailureListener { exception ->
            FancyToast.makeText(context,exception.localizedMessage,
                FancyToast.LENGTH_LONG,
                FancyToast.ERROR,false).show()

        }



    }
}