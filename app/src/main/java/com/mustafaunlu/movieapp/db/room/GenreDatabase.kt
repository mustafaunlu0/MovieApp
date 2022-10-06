package com.mustafaunlu.movieapp.db.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [GenreData::class], version = 1)
abstract class GenreDatabase : RoomDatabase(){

    abstract fun getDAO() : GenreDao

    companion object{

        private var dbINSTANCE : GenreDatabase? = null

        fun getAppDB(context: Context) : GenreDatabase {
            if (dbINSTANCE == null)
                dbINSTANCE =
                    Room.databaseBuilder(context, GenreDatabase::class.java, "genre_database")
                        .allowMainThreadQueries().build()

            return dbINSTANCE!!

        }
    }
}