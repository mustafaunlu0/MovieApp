package com.mustafaunlu.movieapp.ui.fragments.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.mustafaunlu.movieapp.R
import com.mustafaunlu.movieapp.adapter.LikeAdapter
import com.mustafaunlu.movieapp.adapter.MovieAdapter
import com.mustafaunlu.movieapp.adapter.SendLikedMovie
import com.mustafaunlu.movieapp.databinding.FragmentLikeBinding
import com.mustafaunlu.movieapp.models.post.LikedMovie
import com.mustafaunlu.movieapp.viewmodel.MovieViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LikeFragment : Fragment(), SendLikedMovie {

    //BEĞENİLEN FİLMLER COLLECTİON HALİNDE TUTULDUĞUNDAN ALAMIYORUZ
    private val viewModel : MovieViewModel by viewModels()
    private var binding : FragmentLikeBinding? = null

    private lateinit var likeAdapter: LikeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    //View Pager eklenecek
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=FragmentLikeBinding.inflate(inflater,container,false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        likeAdapter= LikeAdapter()
        viewModel.getUserLikedMovie()


        viewModel.likedMovieList.observe(viewLifecycleOwner){
            likeAdapter.setList(it,this)
            likeAdapter.notifyDataSetChanged()
            binding!!.likedRecyclerView.adapter=likeAdapter
            binding!!.likedRecyclerView.layoutManager=LinearLayoutManager(context)
        }



    }

    override fun sendLikedMovie(data: LikedMovie) {
        val action=LikeFragmentDirections.actionLikeFragmentToDetailFragment(data.imageUrl,data.title,data.overview,data.date)
        findNavController().navigate(action)
    }

}