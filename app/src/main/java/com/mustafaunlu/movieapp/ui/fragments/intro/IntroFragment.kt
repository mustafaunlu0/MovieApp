package com.mustafaunlu.movieapp.ui.fragments.intro

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.mustafaunlu.movieapp.R
import com.mustafaunlu.movieapp.databinding.FragmentIntroBinding
import com.mustafaunlu.movieapp.models.promotion.Promotion
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class IntroFragment : Fragment() {

    //View Binding
    private  var binding : FragmentIntroBinding? = null;
    //Promotions
    private lateinit var promotionInternet : Promotion
    private lateinit var promotionMovies : Promotion
    private lateinit var promotionNotification : Promotion
    private var proNumber : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


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
        createPromotions()
        placePromotion()

    }

    private fun placePromotion() {
        promotionInternet.placeData()
        binding!!.promotionNextButton.setOnClickListener {
            println("Number : "+proNumber)
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
                    Toast.makeText(context, "LOADING..",Toast.LENGTH_SHORT).show()
                    proNumber--
                }

            }
        }

        binding!!.promotionPrevButton.setOnClickListener {
            proNumber--
            println("Number : "+proNumber)
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


}