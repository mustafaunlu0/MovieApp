package com.mustafaunlu.movieapp.ui.fragments.feed

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
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
class FeedFragment : Fragment(), GetPostList,
    androidx.appcompat.widget.SearchView.OnQueryTextListener {

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


        binding?.searchBar?.clearFocus()
        binding?.searchBar?.setOnQueryTextListener(this)


        binding?.addStoryFab?.setOnClickListener {
            findNavController().navigate(R.id.action_feedFragment_to_postFragment2)
        }



        viewModel.getPost(this)

        viewModel.getUserPhoto(viewModel.getCurrentUserEmail())



    }


    override fun getPostList(postList: ArrayList<Post>) {


        println("kaç kere gelicen")

        adapter.setList(postList)
        viewModel.setPostList(postList)


        binding!!.feedRecyclerView.adapter=adapter
        binding!!.feedRecyclerView.layoutManager= LinearLayoutManager(context)







    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        println("Text: "+newText)
        viewModel.postList.observe(viewLifecycleOwner){
            if(it!= null){
                var postedList : ArrayList<Post> =viewModel.filterList(newText,it)
                println("boyut: "+postedList.size)
                if(postedList.isEmpty()){

                }else{
                    println("else")
                    adapter.setList(postedList)
                    adapter.notifyDataSetChanged()


                }



            }
        }
        return true
    }



}

