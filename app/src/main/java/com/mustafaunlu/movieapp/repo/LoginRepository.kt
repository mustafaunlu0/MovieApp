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
                    val intent=Intent(context,MainActivity::class.java);
                    context.startActivity(intent)
                    // you dont finish activity -> requireActivity().finish();
                }

            }.addOnFailureListener {
                Toast.makeText(context,it.toString(), Toast.LENGTH_LONG).show()
            }
    }


    private fun createUserWithInfo(email : String, username: String, password: String, selectedPicture : Uri, context: Context){
        println("fonksiyon başı")
        val uuid = UUID.randomUUID()
        val imageName="$uuid.jpg"
        println("imageName: "+imageName)

        val reference= storage.reference
        val imageReference=reference.child("images").child(imageName)

        //önce storage e koy sonra uriyi al firestore a at
        println("selectedPicture: $selectedPicture")

        imageReference.putFile(selectedPicture).addOnSuccessListener {

            val uploadPictureReference=storage.reference.child("images").child(imageName)

            uploadPictureReference.downloadUrl.addOnSuccessListener {
                val downloadUri=it.toString()
                if(auth.currentUser != null){
                    println("DownloadUri: $downloadUri")

                    val postMap= hashMapOf<String,Any>()
                    postMap["downloadUri"] = downloadUri
                    postMap["email"]=email
                    postMap["username"]=username
                    postMap["password"]=password
                    postMap["date"]=com.google.firebase.Timestamp.now()
                    println("collectionUstu")
                    firestore.collection("User").add(postMap).addOnSuccessListener {
                        println("collectionAltı")
                        Toast.makeText(context,"Successful Login!",Toast.LENGTH_LONG).show()
                        val intent=Intent(context,MainActivity::class.java);
                        context.startActivity(intent)
                        // you dont finish activity -> requireActivity().finish();


                    }.addOnFailureListener { exception->
                        Toast.makeText(context,exception.localizedMessage,Toast.LENGTH_LONG).show()
                    }

                }



            }.addOnFailureListener { exception->
                Toast.makeText(context,exception.localizedMessage,Toast.LENGTH_LONG).show()
            }
        }.addOnFailureListener{ exception ->
            Toast.makeText(context,exception.localizedMessage,Toast.LENGTH_LONG).show()

        }

    }

    fun createUser(email : String,username: String, password: String,passwordAgain: String, selectedPicture : Uri,context: Context){
        println("Kayıt tipi: $email$username$password$passwordAgain$selectedPicture")
        if(password == passwordAgain){
            auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener {  task ->
                if(task.isSuccessful){
                    Toast.makeText(context,"Success",Toast.LENGTH_LONG).show()
                    createUserWithInfo(email,username, password, selectedPicture, context)
                }else{
                    Toast.makeText(context,"Failure",Toast.LENGTH_LONG).show()
                }

            }.addOnFailureListener {
                Toast.makeText(context,it.toString(),Toast.LENGTH_LONG).show()

            }
        }else{
            Toast.makeText(context,"Passwords not compatible",Toast.LENGTH_LONG).show()

        }

    }



}