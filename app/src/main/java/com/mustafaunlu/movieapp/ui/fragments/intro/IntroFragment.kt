package com.mustafaunlu.movieapp.ui.fragments.intro

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.navigation.fragment.findNavController
import com.mustafaunlu.movieapp.R
import com.mustafaunlu.movieapp.databinding.FragmentIntroBinding
import com.mustafaunlu.movieapp.models.promotion.Promotion
import com.mustafaunlu.movieapp.db.pref.SessionManager
import com.mustafaunlu.movieapp.db.room.GenreData
import com.mustafaunlu.movieapp.viewmodel.GenreViewModel
import com.mustafaunlu.movieapp.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class IntroFragment : Fragment() {

    //viewModel
    private val viewmodel : HomeViewModel by viewModels()
    private val genreViewModel : GenreViewModel by viewModels()

    //Genre List
    private var genreList : MutableList<GenreData>? = null

    //View Binding
    private  var binding : FragmentIntroBinding? = null;

    //Promotions
    private lateinit var promotionInternet : Promotion
    private lateinit var promotionMovies : Promotion
    private lateinit var promotionNotification : Promotion
    private var proNumber : Int = 0

    //Across
    @Inject
    lateinit var sessionManager: SessionManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        genreList= mutableListOf()





    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentIntroBinding.inflate(inflater,container,false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        viewmodel.getObserverGenre().observe(viewLifecycleOwner){
            if(it != null){
                for(item in it.genres){
                    val genre=GenreData(0,item.id,item.name)
                    genreList!!.add(genre)
                }
                genreViewModel.addAllGenres(genreList!!)
                println("veri eklendi")
            }
            else{
                println("it(genre) null")
            }
        }



            viewmodel.loadGenreData()
            createPromotions()
            placePromotion()








    }


    private fun placePromotion() {
        promotionInternet.placeData()
        binding!!.promotionNextButton.setOnClickListener {
            binding!!.promotionNextButton.startAnimation(clickAnimation())

            proNumber++
            binding!!.promotionPrevButton.visibility=View.VISIBLE
            when(proNumber){

                0 -> {
                    promotionInternet.placeData()
                    binding!!.promotionPrevButton.visibility=View.VISIBLE
                }
                1 -> {
                    promotionMovies.placeData()

                }
                2 -> {
                    promotionNotification.placeData()
                    binding!!.promotionPrevButton.visibility=View.VISIBLE

                }
                else->{
                    //to Intent
                    findNavController().navigate(R.id.action_introFragment_to_mainActivity)
                    proNumber--
                }

            }
        }

        binding!!.promotionPrevButton.setOnClickListener {
            binding!!.promotionPrevButton.startAnimation(clickAnimation())

            proNumber--
            when(proNumber){
                0 ->{
                    promotionInternet.placeData()
                    binding!!.promotionPrevButton.visibility=View.INVISIBLE
                }
                1->{
                    binding!!.promotionPrevButton.visibility=View.VISIBLE
                    promotionMovies.placeData()
                }


            }
        }
    }


    private fun createPromotions(){
        promotionInternet = Promotion("Offline Database","Create your own favorite list navigate your list without internet",R.drawable.internet,binding!!);
        promotionMovies=Promotion("Millions of Movies","Reach millions of movies instantly",R.drawable.movies,binding!!);
        promotionNotification = Promotion("Advance Notification","Let me inform you before the date of the vision of the films in your favorite list",R.drawable.notification,binding!!)
    }

    fun  clickAnimation() : AlphaAnimation {
        return AlphaAnimation(12F,0.01F)
    }



}