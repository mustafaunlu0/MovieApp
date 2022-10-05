package com.mustafaunlu.movieapp.ui.fragments.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.mustafaunlu.movieapp.R
import com.mustafaunlu.movieapp.models.api.Movie
import com.mustafaunlu.movieapp.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val viewModel : HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getObserverRecentMovie().observe(viewLifecycleOwner, object  : Observer<Movie>{
            override fun onChanged(t: Movie?) {
                if(t != null){
                    //println("veri geldi mi"+t.result[0].title+" "+t.result[1].title)
                    println("veri var"+t.results[0].title)

                }else{
                    println("t boş geldi")
                }
            }

        })

        fetchMovies()


    }

    private fun fetchMovies() {
        CoroutineScope(Dispatchers.IO).launch {

            val job1 : Deferred<Unit> = async {
                viewModel.loadRecentMovieData("1")
            }

            job1.await()


        }
    }


}