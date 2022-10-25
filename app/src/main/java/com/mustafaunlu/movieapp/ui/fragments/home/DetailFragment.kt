package com.mustafaunlu.movieapp.ui.fragments.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.mustafaunlu.movieapp.R
import com.mustafaunlu.movieapp.databinding.FragmentDetailBinding
import com.mustafaunlu.movieapp.models.api.Result
import com.mustafaunlu.movieapp.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DetailFragment: Fragment() {

    private var binding : FragmentDetailBinding? = null
    private val viewModel: HomeViewModel by viewModels()
    private val args : DetailFragmentArgs by navArgs()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentDetailBinding.inflate(inflater,container,false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

            binding!!.movieTitleTextView.text=args.title
            binding!!.overviewTextView.text=args.overview
            binding!!.dateTextView.text=args.date
            Glide.with(binding!!.headerImageView).load("https://image.tmdb.org/t/p/w342/"+args.imageUrl).into(binding!!.headerImageView)






            binding!!.prevButton.setOnClickListener {
                findNavController().navigate(R.id.action_detailFragment_to_homeFragment2)
                println("tıklandı")
            }








    }


}