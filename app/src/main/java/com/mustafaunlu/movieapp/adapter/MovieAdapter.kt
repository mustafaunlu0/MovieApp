package com.mustafaunlu.movieapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mustafaunlu.movieapp.R
import com.mustafaunlu.movieapp.models.api.Result

class MovieAdapter (private val isFirstScreen : Boolean =true)  :
    RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    var resultList : List<Result>?= null

    fun setList(resultList : List<Result>){
        this.resultList=resultList
        notifyDataSetChanged()
    }


    class MovieViewHolder(view : View) :RecyclerView.ViewHolder(view){
        private val title= view.findViewById<TextView>(R.id.posterNameTextView)
        private val posterImage=view.findViewById<ImageView>(R.id.posterImageView)
        private val date=view.findViewById<TextView>(R.id.posterDateTextView)
        fun bind(data : Result){
            title.text=data.title
            date.text=data.release_date
            println("Date: "+data.release_date)
            Glide.with(posterImage).load("https://image.tmdb.org/t/p/w342/"+data.poster_path).into(posterImage)


        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_container_movie,parent,false)
        return MovieViewHolder(view)

    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(resultList!![position])
    }

    override fun getItemCount(): Int {
        return if(resultList == null)
            0
        else if(isFirstScreen)
            4
        else
            resultList!!.size
    }
}