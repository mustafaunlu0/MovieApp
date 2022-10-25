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

            FancyToast.makeText(context,"Successful Login!",
                FancyToast.LENGTH_LONG,
                FancyToast.SUCCESS,false).show();

        }.addOnFailureListener { exception ->
            FancyToast.makeText(context,exception.localizedMessage,
                FancyToast.LENGTH_LONG,
                FancyToast.ERROR,false).show();

        }

    }
}