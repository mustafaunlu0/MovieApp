package com.mustafaunlu.movieapp.repo.app

import com.mustafaunlu.movieapp.db.room.GenreDao
import com.mustafaunlu.movieapp.db.room.GenreData
import javax.inject.Inject

class GenreRepository @Inject constructor(private val dao : GenreDao) {

    val resultData : List<GenreData> =dao.readAllData()

    fun addGenre(genre : GenreData){
        dao.addGenre(genre)
    }

    fun addAllGenres(genreList: List<GenreData>){
        dao.addAllGenres(genreList)
    }
    fun readAllGenres(): List<GenreData> {
        return dao.readAllData()
    }

}