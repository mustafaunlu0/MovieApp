package com.mustafaunlu.movieapp.adapter

import com.mustafaunlu.movieapp.models.post.LikedMovie

interface SendLikedMovie {

    fun sendLikedMovie(data : LikedMovie)
}