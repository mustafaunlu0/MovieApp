package com.mustafaunlu.movieapp

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewTreeObserver
import android.view.animation.AnticipateInterpolator
import androidx.core.splashscreen.SplashScreen
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {

        val splashScreen =installSplashScreen()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val content :View =findViewById(android.R.id.content)
        content.viewTreeObserver.addOnDrawListener {
            // sleep is added for now
            Thread.sleep(3000)
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