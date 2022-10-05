package com.mustafaunlu.movieapp.models.api

data class Result(
    val adult : Boolean,
    val backdrop_path : String,
    val genre_ids : List<Int>,
    val id : Int,
    val overview : String,
    val popularity : Double,
    val poster_path : String,
    val release_Date : String,
    val title : String,
    val video : Boolean,
    val vote_average : Double,
    val vote_count : Int,
    var genreString : String
)