package com.mustafaunlu.movieapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mustafaunlu.movieapp.R
import com.mustafaunlu.movieapp.db.room.GenreData
import com.mustafaunlu.movieapp.models.api.Result
import com.mustafaunlu.movieapp.ui.fragments.home.HomeFragment

class MovieAdapter (private val isFirstScreen : Boolean =true)  :
    RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    var resultList : List<Result>?= null
    var genreList : List<GenreData>? = null
    lateinit var listener : SendDataListener

    fun setList(resultList: List<Result>, genreList: List<GenreData>, listener: SendDataListener){
        this.resultList=resultList
        this.genreList=genreList
        this.listener=listener
        notifyDataSetChanged()
    }


    class MovieViewHolder(view : View) :RecyclerView.ViewHolder(view){
        private val title= view.findViewById<TextView>(R.id.posterNameTextView)
        private val posterImage=view.findViewById<ImageView>(R.id.posterImageView)
        private val date=view.findViewById<TextView>(R.id.posterDateTextView)
        private val genre=view.findViewById<TextView>(R.id.posterGenreTextView)
        fun bind(data : Result,genreList: List<GenreData>){
            var genres =""
            title.text=data.title
            date.text=data.release_date

            for(id in data.genre_ids){
                var result=genreList.find { x -> x.genre_id==id }

                if(result != null){

                    genres +=result.genre_name_en +", "
                }
            }
            genres=genres.substring(0,genres.length-2)
            genre.text=genres


            Glide.with(posterImage).load("https://image.tmdb.org/t/p/w342/"+data.poster_path).into(posterImage)


        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_container_movie,parent,false)
        return MovieViewHolder(view)

    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(resultList!![position],genreList!!)
        holder.itemView.setOnClickListener {
            println("problem olabilir-1")
            var resultMovie=resultList!![position]
            println("problem olabilir-2")
            println("result Movie: "+resultMovie.title)
            listener.sendData(resultMovie)
            println("problem olabilir-3")
        }
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