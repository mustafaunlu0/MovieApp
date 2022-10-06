package com.mustafaunlu.movieapp.db.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "genres")
data class GenreData(
    @PrimaryKey(autoGenerate = true)
    val id : Int,
    val genre_id : Int,
    val genre_name_en: String
)