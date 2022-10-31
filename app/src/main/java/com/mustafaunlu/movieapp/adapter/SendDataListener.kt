package com.mustafaunlu.movieapp.adapter

import com.mustafaunlu.movieapp.models.api.Result

interface SendDataListener {
    fun sendData(data: Result, position : Int)
}