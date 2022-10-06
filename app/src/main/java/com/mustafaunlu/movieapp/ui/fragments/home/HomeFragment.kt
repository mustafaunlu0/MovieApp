package com.mustafaunlu.movieapp.ui.fragments.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.mustafaunlu.movieapp.R
import com.mustafaunlu.movieapp.adapter.MovieAdapter
import com.mustafaunlu.movieapp.databinding.FragmentHomeBinding
import com.mustafaunlu.movieapp.models.api.Movie
import com.mustafaunlu.movieapp.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import kotlin.math.abs

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val viewModel : HomeViewModel by viewModels()

    private var binding : FragmentHomeBinding? = null
    private lateinit var movieAdapter: MovieAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentHomeBinding.inflate(inflater,container,false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpViewPager()

        viewModel.getObserverRecentMovie().observe(viewLifecycleOwner, object  : Observer<Movie>{
            override fun onChanged(t: Movie?) {
                if(t != null){
                    //println("veri geldi mi"+t.result[0].title+" "+t.result[1].title)
                    println("veri var"+t.results[0].title)
                    movieAdapter.setList(t.results)


                }else{
                    println("t boş geldi")
                }
            }

        })

        fetchMovies()


    }

    private fun setUpViewPager() {
        movieAdapter= MovieAdapter()
        val movieViewPager : ViewPager2 =binding!!.moviesViewPager
        movieViewPager.clipToPadding=false
        movieViewPager.clipChildren=false
        movieViewPager.offscreenPageLimit=3
        movieViewPager.getChildAt(0).overScrollMode= RecyclerView.OVER_SCROLL_NEVER
        val compositePageTransformer = CompositePageTransformer()
        compositePageTransformer.addTransformer(MarginPageTransformer(10))
        compositePageTransformer.addTransformer { page, position ->
            val r =1 - abs(position)
            //page.scaleY=0.85f + r + 0.15f
            //page.scaleY=0.8f +(r/5)
            page.scaleY=0.8f +(r/5)
            page.scaleX=1f +(r/5)


        }
        movieViewPager.setPageTransformer(compositePageTransformer)
        movieViewPager.adapter=movieAdapter

        println("setUpViewPager")

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