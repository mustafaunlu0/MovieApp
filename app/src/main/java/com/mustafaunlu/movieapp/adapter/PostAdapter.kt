package com.mustafaunlu.movieapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mustafaunlu.movieapp.R
import com.mustafaunlu.movieapp.models.post.Post

class PostAdapter : RecyclerView.Adapter<PostAdapter.PostViewHolder>() {


    //veriyi çek
    var postList : List<Post>? = null

    fun setList(postList: List<Post>){
        this.postList=postList
    }

    class PostViewHolder(view : View) : RecyclerView.ViewHolder(view){

        private val profileImage=view.findViewById<ImageView>(R.id.profileImageView)
        private val username=view.findViewById<TextView>(R.id.usernameTextView)
        private val movie=view.findViewById<TextView>(R.id.movieNameTextView)
        private val category=view.findViewById<TextView>(R.id.categoryTextView)
        private val post=view.findViewById<TextView>(R.id.postedTextView)

        fun bind(data : Post){

        username.text=data.username
        movie.text=data.movie
        category.text=data.category
        post.text=data.post
         // şu an için image yok -> Glide.with(profileImage).load(data.imageUrl).into(profileImage)


        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.feed_row,parent,false)
        return PostViewHolder(view)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.bind(postList!![position])
        holder.itemView.setOnClickListener {
            //henüz bir şey yok
        }
    }

    override fun getItemCount(): Int {
        return postList!!.size
    }
}