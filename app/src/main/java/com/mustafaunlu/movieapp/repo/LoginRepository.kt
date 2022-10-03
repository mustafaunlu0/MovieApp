package com.mustafaunlu.movieapp.repo

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.mustafaunlu.movieapp.ui.activities.MainActivity
import com.shashank.sony.fancytoastlib.FancyToast
import dagger.hilt.android.scopes.FragmentScoped
import java.util.*
import javax.inject.Inject

class LoginRepository @Inject constructor(
    private val auth: FirebaseAuth,
    private val storage : FirebaseStorage,
    private val firestore : FirebaseFirestore
) {


    fun isCurrentUser(context: Context) :Boolean{
        val currentUser=auth.currentUser
        return currentUser != null
    }

    fun signIn(email : String, password :String, context :Context ){
            auth.signInWithEmailAndPassword(email,password).addOnCompleteListener {  task ->
                if(task.isSuccessful){
                    FancyToast.makeText(context,"Login..",
                        FancyToast.LENGTH_LONG,
                        FancyToast.SUCCESS,false).show();
                    //val intent=Intent(context,MainActivity::class.java);
                    //context.startActivity(intent)

                    // you dont finish activity -> requireActivity().finish();
                }

            }.addOnFailureListener {
                FancyToast.makeText(context,it.localizedMessage,
                    FancyToast.LENGTH_LONG,
                    FancyToast.ERROR,false).show();
            }
    }


    private fun createUserWithInfo(email : String, username: String, password: String, selectedPicture : Uri, context: Context){

        val uuid = UUID.randomUUID()
        val imageName="$uuid.jpg"

        val reference= storage.reference
        val imageReference=reference.child("images").child(imageName)


        imageReference.putFile(selectedPicture).addOnSuccessListener {

            val uploadPictureReference=storage.reference.child("images").child(imageName)

            uploadPictureReference.downloadUrl.addOnSuccessListener {
                val downloadUri=it.toString()
                if(auth.currentUser != null){
                    val postMap= hashMapOf<String,Any>()

                    postMap["downloadUri"] = downloadUri
                    postMap["email"]=email
                    postMap["username"]=username
                    postMap["password"]=password
                    postMap["date"]=com.google.firebase.Timestamp.now()
                    println("collectionUstu")

                    firestore.collection("User").add(postMap).addOnSuccessListener {

                        FancyToast.makeText(context,"Successful Login!",
                            FancyToast.LENGTH_LONG,
                            FancyToast.SUCCESS,false).show();

                        //val intent=Intent(context,MainActivity::class.java);
                        //context.startActivity(intent)
                        // you dont finish activity -> requireActivity().finish();


                    }.addOnFailureListener { exception->

                        FancyToast.makeText(context,exception.localizedMessage,
                            FancyToast.LENGTH_LONG,
                            FancyToast.ERROR,false).show();

                    }

                }



            }.addOnFailureListener { exception->
                FancyToast.makeText(context,exception.localizedMessage,
                    FancyToast.LENGTH_LONG,
                    FancyToast.ERROR,false).show();
            }
        }.addOnFailureListener{ exception ->
            FancyToast.makeText(context,exception.localizedMessage,
                FancyToast.LENGTH_LONG,
                FancyToast.ERROR,false).show();

        }

    }

    fun createUser(email : String,username: String, password: String,passwordAgain: String, selectedPicture : Uri,context: Context){


        if(password == passwordAgain){
            auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener {  task ->
                if(task.isSuccessful){
                    FancyToast.makeText(context,"Success!",
                        FancyToast.LENGTH_LONG,
                        FancyToast.SUCCESS,false).show();
                    createUserWithInfo(email,username, password, selectedPicture, context)
                }else{
                    FancyToast.makeText(context,"Failure!",
                        FancyToast.LENGTH_LONG,
                        FancyToast.ERROR,false).show();
                }

            }.addOnFailureListener {
                FancyToast.makeText(context,it.localizedMessage,
                    FancyToast.LENGTH_LONG,
                    FancyToast.ERROR,false).show();

            }
        }else{
            FancyToast.makeText(context,"Passwords not compatible",
                FancyToast.LENGTH_LONG,
                FancyToast.CONFUSING,false).show();

        }

    }



}