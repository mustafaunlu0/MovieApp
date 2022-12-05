package com.mustafaunlu.movieapp.ui.fragments.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.mustafaunlu.movieapp.R
import com.mustafaunlu.movieapp.adapter.PostAdapter
import com.mustafaunlu.movieapp.adapter.SendDataListener
import com.mustafaunlu.movieapp.databinding.FragmentProfileBinding
import com.mustafaunlu.movieapp.models.api.Result
import com.mustafaunlu.movieapp.viewmodel.MovieViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : Fragment(){

    val viewModel : MovieViewModel by viewModels()
    private var binding : FragmentProfileBinding? = null
    private var adapter=PostAdapter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=FragmentProfileBinding.inflate(inflater,container,false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //profile image
        viewModel.getUserPhoto(viewModel.getCurrentUserEmail())
        viewModel.profileImage.observe(viewLifecycleOwner){ imageUri ->
            Glide.with(binding!!.imageProfile).load(imageUri).into(binding!!.imageProfile)
        }


        //username - email
        viewModel.findUserName(viewModel.getCurrentUserEmail())
        viewModel.getUsername().observe(viewLifecycleOwner){ username->
            binding!!.artistTextView.text=username.toString()
            binding!!.aboutTextView.text=viewModel.getCurrentUserEmail()
            viewModel.getSelectedPost(username)
        }
        viewModel.selectedPostList.observe(viewLifecycleOwner){ postList->
            println("selectedPostList")
            adapter.setList(postList)
            binding!!.profileRecyclerView.adapter=adapter
            binding!!.profileRecyclerView.layoutManager=LinearLayoutManager(context)
        }



    }






}