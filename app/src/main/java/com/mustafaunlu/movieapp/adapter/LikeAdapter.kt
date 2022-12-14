package com.mustafaunlu.movieapp.adapter

import android.view.LayoutInflater
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.mustafaunlu.movieapp.R
import com.mustafaunlu.movieapp.models.post.LikedMovie

class LikeAdapter : RecyclerView.Adapter<LikeAdapter.LikeViewHolder>() {

    var likeList : List<LikedMovie>? = null
    lateinit var listener : SendLikedMovie

    fun setList(likeList : List<LikedMovie>,listener : SendLikedMovie){
        this.likeList=likeList
        this.listener=listener
    }


    class LikeViewHolder(view : View) : RecyclerView.ViewHolder(view){

    private var movieName=view.findViewById<TextView>(R.id.movieNameTextView)
        fun bind(data : LikedMovie){
            movieName.text=data.title
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LikeViewHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.like_row,parent,false)
        return LikeViewHolder(view)
    }

    override fun onBindViewHolder(holder: LikeViewHolder, position: Int) {
        holder.bind(likeList!![position])
        holder.itemView.setOnClickListener {
            listener.sendLikedMovie(likeList!![position])
        }
    }

    override fun getItemCount(): Int {
        return likeList!!.size
    }
}