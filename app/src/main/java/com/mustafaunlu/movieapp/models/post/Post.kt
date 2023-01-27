package com.mustafaunlu.movieapp.models.post

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Post(val id :String, val username : String, val movie : String, val category : String, val post : String,
                var numberOfLike : Int,var numberOfDislike : Int, var date : String) :
    Parcelable