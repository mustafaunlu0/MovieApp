package com.mustafaunlu.movieapp.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewTreeObserver
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.mustafaunlu.movieapp.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen =installSplashScreen()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        val content : View =findViewById(android.R.id.content)
        content.viewTreeObserver.addOnDrawListener {
            object  : ViewTreeObserver.OnPreDrawListener{

                override fun onPreDraw(): Boolean {
                    //ViewModel ready will add
                    return if(true){
                        content.viewTreeObserver.removeOnPreDrawListener(this)
                        true
                    }else{
                        false

                    }
                }


            }
        }
    }
}