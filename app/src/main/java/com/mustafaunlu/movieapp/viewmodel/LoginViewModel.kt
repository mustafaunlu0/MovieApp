package com.mustafaunlu.movieapp.viewmodel

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import com.mustafaunlu.movieapp.repo.LoginRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlin.math.log

@HiltViewModel
class LoginViewModel @Inject constructor(private val loginRepository: LoginRepository) :ViewModel() {



    //Repodaki suspend değil!
    fun signIn(email :String, password :String, context: Context){
        loginRepository.signIn(email,password,context)
    }
    fun isCurrentUser(context: Context) : Boolean{
        return loginRepository.isCurrentUser(context)
    }

    fun signUp(email : String, username: String, password: String ,passwordAgain :String, selectedPicture : Uri, context: Context){
        loginRepository.createUser(email,username,password,passwordAgain, selectedPicture,context)
    }
}