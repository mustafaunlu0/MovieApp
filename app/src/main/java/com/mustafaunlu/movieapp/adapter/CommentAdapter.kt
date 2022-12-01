package com.mustafaunlu.movieapp.adapter

import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.mustafaunlu.movieapp.R
import com.mustafaunlu.movieapp.models.post.Comment

class CommentAdapter : RecyclerView.Adapter<CommentAdapter.CommentViewHolder>() {


    var commentList : List<Comment>? = null

    fun setList(commentList : List<Comment>){
        this.commentList=commentList
        notifyDataSetChanged()
    }

    class CommentViewHolder(view : View) : RecyclerView.ViewHolder(view){

        private var username=view.findViewById<TextView>(R.id.postedUsernameTextView)
        private var comment=view.findViewById<TextView>(R.id.postedCommentTextView)
        private var firestore: FirebaseFirestore = Firebase.firestore
        private var profileImage=view.findViewById<ImageView>(R.id.commentDetailProfileImageView)

        fun bind(data : Comment){
            username.text=data.username
            comment.text=data.comment

            firestore.collection("User").get().addOnSuccessListener {
                for (item in it){
                    if(item.data["username"].toString()==data.username){
                        Glide.with(profileImage).load(item.data["downloadUri"].toString()).into(profileImage)
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.comment_row,parent,false)
        return CommentViewHolder(view)
    }

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        holder.bind(commentList!![position])
    }

    override fun getItemCount(): Int {
        return commentList!!.size
    }
}