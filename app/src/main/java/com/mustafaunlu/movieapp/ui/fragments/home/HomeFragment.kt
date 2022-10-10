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
import com.bumptech.glide.Glide
import com.mustafaunlu.movieapp.R
import com.mustafaunlu.movieapp.adapter.MovieAdapter
import com.mustafaunlu.movieapp.databinding.FragmentHomeBinding
import com.mustafaunlu.movieapp.db.room.GenreData
import com.mustafaunlu.movieapp.models.api.Movie
import com.mustafaunlu.movieapp.models.api.Result
import com.mustafaunlu.movieapp.viewmodel.GenreViewModel
import com.mustafaunlu.movieapp.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import kotlin.math.abs

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val viewModel : HomeViewModel by viewModels()
    private val genreViewModel : GenreViewModel by viewModels()

    private var binding : FragmentHomeBinding? = null
    private lateinit var movieAdapter: MovieAdapter

    private var genreList : List<GenreData>? = null

    private lateinit var movieViewPager : ViewPager2


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



        viewModel.getObserverRecentMovie().observe(viewLifecycleOwner
        ) { t ->
            if (t != null) {
                movieAdapter.setList(t.results,genreList!!)


            } else {
                println("t boş geldi")
            }
        }
        viewModel.getObserverPopularMovie().observe(viewLifecycleOwner){

            if(it != null){

                val rndm=(0..19).random()
                val randomMovie=it.results[rndm]
                println("random sayı : $rndm")
                placePopularMovie(randomMovie)
            }else{
                println("t null geldi")
            }
        }



        genreViewModel.getRecordsObserver().observe(viewLifecycleOwner,object : Observer<List<GenreData>>{
            override fun onChanged(t: List<GenreData>?) {
                if(t != null){
                    genreList=t
                    fetchMovies()
                }else{

                }
            }

        })


        fetchMovies()


    }
    private fun placePopularMovie(randomMovie : Result){

        var genres =""
        binding!!.recommendedTitleTextView.text=randomMovie.title
        binding!!.recommendedDateTextView.text=randomMovie.release_date
        println("oran :" +randomMovie.vote_average)
        binding!!.ratingBar.rating= (randomMovie.vote_average/2).toFloat()
        for(id in randomMovie.genre_ids){
            var result=genreList!!.find { x -> x.genre_id==id }
            if(result != null){
                genres +=result.genre_name_en +", "
            }
        }
        genres=genres.substring(0,genres.length-2)
        binding!!.recommendedGenreTextView.text=genres
        Glide.with(binding!!.recommendedImageView).load("https://image.tmdb.org/t/p/w342/"+randomMovie.poster_path).into(binding!!.recommendedImageView)


    }


    private fun setUpViewPager() {
        movieAdapter= MovieAdapter()
        movieViewPager =binding!!.moviesViewPager
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


    }

    private fun fetchMovies() {
        CoroutineScope(Dispatchers.IO).launch {

            val job1 : Deferred<Unit> = async {
                viewModel.loadRecentMovieData("1")
            }
            val job2 : Deferred<Unit> = async {
                viewModel.loadPopularMovieData("1")
            }


            job1.await()
            job2.await()

        }
    }


}