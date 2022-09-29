package com.mustafaunlu.movieapp.repo

import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.mustafaunlu.movieapp.ui.activities.MainActivity
import dagger.hilt.android.scopes.FragmentScoped
import javax.inject.Inject

class LoginRepository @Inject constructor(
    private val auth: FirebaseAuth,
) {


    fun isCurrentUser(context: Context) :Boolean{
        val currentUser=auth.currentUser
        return currentUser != null
    }

    fun signIn(email : String, password :String, context :Context ){
        if(email.isNotEmpty() && password.isNotEmpty()){

            auth.signInWithEmailAndPassword(email,password).addOnCompleteListener {  task ->
                if(task.isSuccessful){
                    val intent=Intent(context,MainActivity::class.java);
                    context.startActivity(intent)
                    // you dont finish activity -> requireActivity().finish();

                }

            }.addOnFailureListener {
                Toast.makeText(context,it.toString(), Toast.LENGTH_LONG).show()

            }

        }else{
            Toast.makeText(context,"Fill the blanks!", Toast.LENGTH_LONG).show()
        }
    }
}