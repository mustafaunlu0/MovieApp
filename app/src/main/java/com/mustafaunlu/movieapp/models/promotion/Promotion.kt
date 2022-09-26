package com.mustafaunlu.movieapp.models.promotion

import android.media.Image
import android.widget.ImageView
import com.mustafaunlu.movieapp.databinding.FragmentIntroBinding

class Promotion(private val promotionTitle: String, private val promotionExp: String, private val promotionImage: Int, private val binding: FragmentIntroBinding){

     fun placeData(){
         //First
         binding.promotionImageView.setImageResource(promotionImage)
         binding.promotionTitleTextView.text = promotionTitle
         binding.promotionExpTextView.text =promotionExp

     }

 }