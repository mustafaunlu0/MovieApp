package com.mustafaunlu.movieapp.db.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface GenreDao {

    @Insert
    fun addGenre(genre : GenreData)

    @Insert
    fun addAllGenres(genre: GenreData)

    @Query("SELECT * FROM genres")
    fun readAllData() : List<GenreData>

}