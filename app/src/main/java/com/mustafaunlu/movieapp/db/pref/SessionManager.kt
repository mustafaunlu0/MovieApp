package com.mustafaunlu.movieapp.db.pref

import android.content.SharedPreferences
import com.mustafaunlu.movieapp.util.Constants
import javax.inject.Inject

class SessionManager @Inject constructor(
    private val preferences : SharedPreferences
) {

    fun getIsFirstRun() = preferences.getBoolean(Constants.FIRST_RUN_KEY,true)

    fun setIsFirstRun(value : Boolean){
        val editor=preferences.edit()
        editor.putBoolean(Constants.FIRST_RUN_KEY,value)
        editor.apply()
    }



}