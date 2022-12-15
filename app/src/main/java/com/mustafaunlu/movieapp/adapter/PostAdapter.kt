package com.mustafaunlu.movieapp.adapter

import android.media.Image
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.mustafaunlu.movieapp.R
import com.mustafaunlu.movieapp.models.api.Movie
import com.mustafaunlu.movieapp.models.post.Post
import com.mustafaunlu.movieapp.repo.app.HomeRepository
import com.mustafaunlu.movieapp.viewmodel.HomeViewModel
import com.mustafaunlu.movieapp.viewmodel.MovieViewModel
import com.shashank.sony.fancytoastlib.FancyToast
import org.w3c.dom.Text
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
        var numberOfLike=view.findViewById<TextView>(R.id.numberOfLike)
        var numberOfLikeButton=view.findViewById<ImageView>(R.id.numberOfLikeButton)
        private var date=view.findViewById<TextView>(R.id.postDateTextView)

        private var like : Boolean=true


        fun bind(data : Post){

        username.text=data.username
        movie.text=data.movie
        category.text=data.category
        post.text=data.post
        date.text=data.date
        numberOfLike.text=data.numberOfLike.toString()
            firestore.collection("User").get().addOnSuccessListener {
                for (item in it){
                    if(item.data["username"].toString()==data.username){
                        Glide.with(profileImage).load(item.data["downloadUri"].toString()).into(profileImage)
                    }
                }
            }

        }
        fun incLike(postData : Post){
            if (like){
                firestore.collection("Posts").get().addOnSuccessListener {
                    for(item in it){
                        if(item.data["id"].toString()==postData.id){
                            println("item-id: "+item.id)

                            //Birden fazla like atıyor
                            firestore.collection("Posts").document(item.id).update("numberOfLike",postData.numberOfLike++).addOnSuccessListener {
                                var likeMap=HashMap<String,Any>()
                                likeMap["user"]=postData.username
                                firestore.collection("Post-Details").document(postData.id).collection("Usernames").add(likeMap).addOnSuccessListener {
                                    FancyToast.makeText(itemView.context, "Liked!",
                                        FancyToast.LENGTH_LONG,
                                        FancyToast.SUCCESS,false).show();
                                    like=false

                                }
                            }
                        }
                    }

                }
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.feed_row,parent,false)
        return PostViewHolder(view)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {

        holder.bind(postList!![position])
        holder.itemView.setOnClickListener {
            /*
            val bundle = bundleOf("post" to postList!![position])

            if(holder.itemView.findNavController().currentDestination?.id==R.id.feedFragment){
                //Feed Fragment
                println("feed")
                holder.itemView.findNavController().navigate(R.id.action_feedFragment_to_commentFragment,bundle)
            }

            else{
                println("profile")
                holder.itemView.findNavController().navigate(R.id.action_profileFragment_to_commentFragment2,bundle)

            }

             */

        }
        holder.numberOfLikeButton.setOnClickListener {
            println("tıklandı")
            holder.incLike(postList!![position])
            notifyDataSetChanged()

        }


    }

    override fun getItemCount(): Int {
        return postList!!.size
    }
}