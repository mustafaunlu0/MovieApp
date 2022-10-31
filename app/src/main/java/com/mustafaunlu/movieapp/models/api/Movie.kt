package com.mustafaunlu.movieapp.models.api

data class Movie(
    val page : Int,
    var results : List<Result>,
    val total_pages : Int,
    val total_results : Int
)