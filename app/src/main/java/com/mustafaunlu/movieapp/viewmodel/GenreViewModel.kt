package com.mustafaunlu.movieapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mustafaunlu.movieapp.db.room.GenreData
import com.mustafaunlu.movieapp.repo.app.GenreRepository
import com.mustafaunlu.movieapp.repo.retrofit.RetrofitRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GenreViewModel @Inject constructor(
    private val genreRepository: GenreRepository
) : ViewModel() {

    var allData : MutableLiveData<List<GenreData>> = MutableLiveData()

    init {
        loadRecords()
    }

    fun getRecordsObserver() : MutableLiveData<List<GenreData>>{
        return allData
    }

    fun addAllGenres(genreList: List<GenreData>){
        genreRepository.addAllGenres(genreList)
        loadRecords()
    }
    fun readAllGenres(): List<GenreData> {
        return genreRepository.readAllGenres()
    }


    private fun loadRecords(){
        var list=genreRepository.resultData
        allData.postValue(list)
    }






}