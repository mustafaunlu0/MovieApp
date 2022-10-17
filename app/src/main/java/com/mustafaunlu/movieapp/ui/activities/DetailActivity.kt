package com.mustafaunlu.movieapp.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.mustafaunlu.movieapp.R
import com.mustafaunlu.movieapp.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {

    private var binding : ActivityDetailBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding!!.root)


        val intent=intent
        val overview=intent.getStringExtra("overview")
        val title=intent.getStringExtra("title")
        val url=intent.getStringExtra("url")
        val date=intent.getStringExtra("date")

        binding!!.movieTitleTextView.text=title
        binding!!.overviewTextView.text=overview
        binding!!.dateTextView.text=date

        Glide.with(binding!!.headerImageView).load("https://image.tmdb.org/t/p/w342/"+url).into(binding!!.headerImageView)

        binding!!.prevButton.setOnClickListener {
            val intentToMain=Intent(this,MainActivity::class.java)
            startActivity(intentToMain)
        }
    }



}