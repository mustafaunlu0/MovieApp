package com.mustafaunlu.movieapp.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.mustafaunlu.movieapp.R
import com.mustafaunlu.movieapp.models.api.Movie
import com.mustafaunlu.movieapp.models.post.Post
import com.mustafaunlu.movieapp.repo.app.HomeRepository
import com.mustafaunlu.movieapp.viewmodel.HomeViewModel
import com.mustafaunlu.movieapp.viewmodel.MovieViewModel
import javax.inject.Inject

class PostAdapter : RecyclerView.Adapter<PostAdapter.PostViewHolder>() {



    //veriyi çek
    var postList : List<Post>? = null

    fun setList(postList: List<Post>){
        this.postList=postList

    }




    class PostViewHolder(view : View) : RecyclerView.ViewHolder(view){


        private var firestore: FirebaseFirestore= Firebase.firestore
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
            println("collection üstü")
            firestore.collection("User").get().addOnSuccessListener {
                println("varan 1")
                for (item in it){
                    println("varan2")
                    if(item.data["username"].toString()==data.username){
                        println("burasıcollection")
                        Glide.with(profileImage).load(item.data["downloadUri"].toString()).into(profileImage)
                    }
                }
            }
            println("collection altı")

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