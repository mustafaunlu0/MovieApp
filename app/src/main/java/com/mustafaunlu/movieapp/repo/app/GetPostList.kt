package com.mustafaunlu.movieapp.repo.app

import com.mustafaunlu.movieapp.models.post.Post

interface GetPostList {

    fun getPostList(postList : ArrayList<Post>)
}