package com.mustafaunlu.movieapp.ui.fragments.home

import android.animation.Animator
import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.mustafaunlu.movieapp.R
import com.mustafaunlu.movieapp.adapter.MovieAdapter
import com.mustafaunlu.movieapp.adapter.SendDataListener
import com.mustafaunlu.movieapp.databinding.FragmentHomeBinding
import com.mustafaunlu.movieapp.db.pref.SessionManager
import com.mustafaunlu.movieapp.db.room.GenreData
import com.mustafaunlu.movieapp.models.api.Result
import com.mustafaunlu.movieapp.viewmodel.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import kotlinx.coroutines.NonCancellable.isActive
import java.lang.Thread.sleep
import javax.inject.Inject
import kotlin.math.abs
import kotlin.random.Random

@AndroidEntryPoint
class HomeFragment : Fragment(), SendDataListener, FirebaseCallback{

    private val viewModel : HomeViewModel by viewModels()
    private val movieViewModel : MovieViewModel by viewModels()
    private val genreViewModel : GenreViewModel by viewModels()

    //Animation

    private var binding : FragmentHomeBinding? = null
    private lateinit var movieAdapter: MovieAdapter

    private var genreList : List<GenreData>? = null

    private lateinit var movieViewPager : ViewPager2

    private lateinit var randomMovie : Result




    //data neden değişmiyor!


    @Inject
    lateinit var sessionManager: SessionManager

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
                //println("position- Observer: "+sessionManager.getPosition())
                //gelen veriyi filtreleme ya da database e çekip onun üzerinde filtreleme ya da adapter üzerinde filtreleme
                movieAdapter.setList(t.results,genreList!!,this)


            } else {
                //println("t boş geldi")
            }
        }
        viewModel.getObserverPopularMovie().observe(viewLifecycleOwner){

            if(it != null){

                val rndm=(0..19).random()
                randomMovie=it.results[rndm]

                placePopularMovie(randomMovie)
            }else{
                //println("t null geldi")
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

        binding!!.imageLayout.setOnClickListener {
            val action=HomeFragmentDirections.actionHomeFragment2ToDetailFragment(randomMovie.poster_path,randomMovie.title,randomMovie.overview,randomMovie.release_date)
            findNavController().navigate(action)
        }


        binding!!.favoriteImageView.setOnClickListener{

            movieViewModel.likeMovie(randomMovie.poster_path,randomMovie.title,randomMovie.overview,randomMovie.release_date,requireContext())
            binding!!.favoriteImageView.isVisible=false

        }


        binding!!.favoriteImageView.pauseAnimation()
        binding!!.favoriteImageView.setOnClickListener{
            binding!!.favoriteImageView.playAnimation()
            movieViewModel.likeMovie(randomMovie.poster_path,randomMovie.title,randomMovie.overview,randomMovie.release_date,requireContext())
            object : CountDownTimer(3000,800){
                override fun onTick(p0: Long) {
                    println("sayac: "+p0)
                    if(p0 < 2300L){
                        binding!!.favoriteImageView.pauseAnimation()
                        binding!!.favoriteImageView.isVisible=false

                    }
                }

                override fun onFinish() {
                    println("bitti")

                }

            }.start()

            //binding!!.likeImageView.isVisible=false

            //findNavController().navigate(R.id.action_homeFragment2_to_likeFragment)
        }

        binding!!.likedImageView.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment2_to_likeFragment)
        }


    }



    private fun placePopularMovie(randomMovie : Result){

        var genres =""
        binding!!.recommendedTitleTextView.text=randomMovie.title
        binding!!.recommendedDateTextView.text=randomMovie.release_date
        binding!!.ratingBar.rating= (randomMovie.vote_average/2).toFloat()
        for(id in randomMovie.genre_ids){
            var result=genreList!!.find { x -> x.genre_id==id }
            if(result != null){
                genres +=result.genre_name_en +", "
            }
        }
        //Burada patlak verebiliyor kontrol et
        genres=genres.substring(0,genres.length-2)
        binding!!.recommendedGenreTextView.text=genres
        Glide.with(binding!!.recommendedImageView).load("https://image.tmdb.org/t/p/w342/"+randomMovie.poster_path).into(binding!!.recommendedImageView)


        movieViewModel.getLikedMovie(randomMovie.title,this)

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
            page.scaleY=0.8f +(r/5)
            page.scaleX=1f +(r/5)


        }
        movieViewPager.setPageTransformer(compositePageTransformer)
        movieViewPager.adapter=movieAdapter


    }

    private fun fetchMovies() {
        CoroutineScope(Dispatchers.IO).launch {
            val random= Random.nextInt(0,5)
            val job1 : Deferred<Unit> = async {
                viewModel.loadRecentMovieData(random.toString())
            }
            val job2 : Deferred<Unit> = async {
                viewModel.loadPopularMovieData(random.toString())
            }


            job1.await()
            job2.await()

        }
    }

    override fun sendData(data: Result, position : Int) {
        sessionManager.setPosition(position)
        val action=HomeFragmentDirections.actionHomeFragment2ToDetailFragment(data.poster_path,data.title,data.overview,data.release_date)
        findNavController().navigate(action)



    }

    override fun onResponse(size: Int) {
        binding!!.favoriteImageView.isVisible = size <= 0
    }




}