package com.mustafaunlu.movieapp.ui.fragments.feed

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.mustafaunlu.movieapp.R
import com.mustafaunlu.movieapp.adapter.PostAdapter
import com.mustafaunlu.movieapp.databinding.FragmentFeedBinding
import com.mustafaunlu.movieapp.models.post.Post
import com.mustafaunlu.movieapp.repo.app.GetPostList
import com.mustafaunlu.movieapp.viewmodel.MovieViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FeedFragment : Fragment(), GetPostList {

    private var binding : FragmentFeedBinding? = null
    private val viewModel : MovieViewModel by viewModels()
    private var adapter=PostAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentFeedBinding.inflate(inflater,container,false)
        return binding?.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.addStoryFab?.setOnClickListener {
            findNavController().navigate(R.id.action_feedFragment_to_postFragment2)
        }



        viewModel.getUserPhoto(viewModel.getCurrentUserEmail())
        viewModel.getProfileImage().observe(viewLifecycleOwner){ profileImage ->
            adapter.setProfilePhoto(profileImage)
            viewModel.getPost(this)
        }


        viewModel.getCurrentUserEmail()

        //Tasarımı düzeltmesi kaldı


    }

    override fun getPostList(postList: ArrayList<Post>) {

        adapter.setList(postList)

        for(item in postList){
            println("Film: "+item.movie)
        }

        binding!!.feedRecyclerView.adapter=adapter
        binding!!.feedRecyclerView.layoutManager=LinearLayoutManager(context)

    }


}