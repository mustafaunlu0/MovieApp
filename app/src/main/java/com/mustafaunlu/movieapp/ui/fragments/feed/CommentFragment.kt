package com.mustafaunlu.movieapp.ui.fragments.feed

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.mustafaunlu.movieapp.R
import com.mustafaunlu.movieapp.adapter.CommentAdapter
import com.mustafaunlu.movieapp.databinding.FragmentCommentBinding
import com.mustafaunlu.movieapp.databinding.FragmentFeedBinding
import com.mustafaunlu.movieapp.models.post.Post
import com.mustafaunlu.movieapp.viewmodel.MovieViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class CommentFragment : Fragment() {

    private var binding : FragmentCommentBinding? = null
    private val viewModel : MovieViewModel by viewModels()

    private var commentAdapter = CommentAdapter()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentCommentBinding.inflate(inflater,container,false)
        return binding?.root
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val post=arguments?.get("post") as Post

        viewModel.getUserPhotoWithUsername(post.username)
        viewModel.profileImage.observe(viewLifecycleOwner){
            println("image"+it)
            Glide.with(binding!!.commentProfileImageView).load(it).into(binding!!.commentProfileImageView)
        }

        binding!!.commentUsernameTextView.text=post.username
        binding!!.commentMovieNameTextView.text=post.movie
        binding!!.commentCategoryTextView.text=post.category
        binding!!.commentPostedTextView.text=post.post


        binding!!.commentSendButton.setOnClickListener{
            if(binding!!.commentEditText.text.toString().isNotEmpty()){
                viewModel.findUserName(viewModel.getCurrentUserEmail())
                viewModel.getUsername().observe(viewLifecycleOwner){
                    binding!!.commentRecyclerView.adapter!!.notifyDataSetChanged()
                    viewModel.addComment(post.id,it,binding!!.commentEditText.text.toString(),requireContext())
                    binding!!.commentEditText.setText("")
                }

            }
        }

        viewModel.getComments(post.id)
        //CommentList boş geliyor!!
        viewModel.commentList.observe(viewLifecycleOwner){
            println("boyut: "+it.size)
            commentAdapter.setList(it)
            binding!!.commentRecyclerView.adapter=commentAdapter
            binding!!.commentRecyclerView.layoutManager=LinearLayoutManager(requireContext())
        }





    }


}