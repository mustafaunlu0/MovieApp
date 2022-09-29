package com.mustafaunlu.movieapp.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import com.mustafaunlu.movieapp.repo.LoginRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlin.math.log

@HiltViewModel
class LoginViewModel @Inject constructor(private val loginRepository: LoginRepository) :ViewModel() {



    //Repodaki suspend değil!
    fun signIn(email :String,password :String,context: Context){
        loginRepository.signIn(email,password,context)
    }
    fun isCurrentUser(context: Context) : Boolean{
        return loginRepository.isCurrentUser(context)
    }
}