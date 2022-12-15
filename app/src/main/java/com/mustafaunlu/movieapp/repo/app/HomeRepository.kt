package com.mustafaunlu.movieapp.repo.app

import android.content.Context
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.type.Date
import com.google.type.DateTime
import com.mustafaunlu.movieapp.models.post.Comment
import com.mustafaunlu.movieapp.models.post.LikedMovie
import com.mustafaunlu.movieapp.models.post.Post
import com.mustafaunlu.movieapp.viewmodel.FirebaseCallback
import com.shashank.sony.fancytoastlib.FancyToast
import java.sql.Timestamp
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

class HomeRepository @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val auth : FirebaseAuth
){
    private var postList = ArrayList<Post>()

    fun currentUser() : String{
        return auth.currentUser?.email.toString()
    }

    fun logout(){
        auth.signOut()
    }

    fun likeMovie(title : String,overview: String,date : String,imageUri : String, context : Context ){

        //For Admin
        val likeMap= hashMapOf<String,Any>()
        likeMap["title"]=title

        //For Admin
        firestore.collection("Liked-Movie").document(currentUser()).collection(title).add(likeMap).addOnSuccessListener {

            FancyToast.makeText(context,"Liked!",
                FancyToast.LENGTH_LONG,
                FancyToast.SUCCESS,false).show()

        }.addOnFailureListener { exception ->
            FancyToast.makeText(context,exception.localizedMessage,
                FancyToast.LENGTH_LONG,
                FancyToast.ERROR,false).show()

        }

        println("kullanici: "+currentUser())
        //For User
        likeMovieForUser(title,overview,date,imageUri, context)

    }

    fun likeMovieForUser(title: String,overview:String,date : String,imageUri : String,context: Context){
        val likeMapUser = hashMapOf<String,Any>()
        likeMapUser["usermail"]=currentUser()
        likeMapUser["title"]=title
        likeMapUser["overview"]=overview
        likeMapUser["date"]=date
        likeMapUser["imageUri"]= "https://image.tmdb.org/t/p/w342/$imageUri"
        firestore.collection("Liked-Movie-User").add(likeMapUser).addOnSuccessListener {
            //Added
            Toast.makeText(context,"Liked-Movie-User added!",Toast.LENGTH_LONG).show()
        }.addOnFailureListener { exception ->
            FancyToast.makeText(context,exception.localizedMessage,
                FancyToast.LENGTH_LONG,
                FancyToast.ERROR,false).show()

        }
    }
    //BEĞENİLEN FİLMLER COLLECTİON HALİNDE TUTULDUĞUNDAN ALAMIYORUZ
    fun getUserLikedMovie(likedList : MutableLiveData<ArrayList<LikedMovie>>){
        var likedArrayList= arrayListOf<LikedMovie>()
        firestore.collection("Liked-Movie-User").get().addOnSuccessListener{
            for(item in it){
                likedArrayList.add(LikedMovie(item.data["title"].toString(),item.data["overview"].toString(),item.data["date"].toString(),item.data["imageUri"].toString()))
            }
            likedList.postValue(likedArrayList)

        }

    }

    fun getLikedMovie(movieName: String, callback: FirebaseCallback){
        firestore.collection("Liked-Movie").document(currentUser()).collection(movieName).get().addOnSuccessListener {
            callback.onResponse(it.size())
        }
    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun postMovie(username: String, movName : String, category: String, postText : String, context: Context){
        val postMap= hashMapOf<String,Any>()
        val uuid=UUID.randomUUID()
        postMap["id"]=uuid.toString()
        postMap["movName"]=movName
        postMap["category"]=category
        postMap["postText"]=postText
        postMap["username"]=username
        postMap["numberOfLike"]=0
        //postMap["date"]=com.google.firebase.Timestamp.now()
        //Date
        val current = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)
        val formatted = current.format(formatter)
        postMap["date"]=formatted

        firestore.collection("Posts").add(postMap).addOnSuccessListener {
            FancyToast.makeText(context,"Posted!",
                FancyToast.LENGTH_LONG,
                FancyToast.SUCCESS,false).show()

        }.addOnFailureListener { exception ->
            FancyToast.makeText(context,exception.localizedMessage,
                FancyToast.LENGTH_LONG,
                FancyToast.ERROR,false).show()

        }



    }

    fun getPost(callback : GetPostList): ArrayList<Post> {

        firestore.collection("Posts").orderBy("date", Query.Direction.DESCENDING).get().addOnSuccessListener {
            for (item in it){
                postList.add(Post(item.data["id"].toString(),item.data["username"].toString(),item.data["movName"].toString(),item.data["category"].toString(),item.data["postText"].toString(),item.data["numberOfLike"].toString().toInt(),item.data["date"].toString()))
                callback.getPostList(postList)
            }
        }
        return postList
    }
    fun likePost(){
        firestore.collection("Posts").get().addOnSuccessListener {

            for(item in it) {

            }
        }
    }

    fun getSelectedPost(username: String, selectedPost : MutableLiveData<ArrayList<Post>>){
        var selectedPostArrayList : ArrayList<Post> = arrayListOf()
        firestore.collection("Posts").orderBy("date", Query.Direction.DESCENDING).get().addOnSuccessListener {
            for (item in it){
                if(item.data["username"].toString()==username){
                    selectedPostArrayList.add(Post(item.data["id"].toString(),item.data["username"].toString(),item.data["movName"].toString(),item.data["category"].toString(),item.data["postText"].toString(),item.data["numberOfLike"].toString().toInt(),item.data["date"].toString()))
                }
            }
            selectedPost.postValue(selectedPostArrayList)
        }

    }
    fun findUserName(userMail: String,username: MutableLiveData<String>){
        println("Home Repository-> findUserName()")
        println("UserMail=$userMail")
        firestore.collection("User").get().addOnSuccessListener {
            for (item in it) {
                if (item.data["email"].toString().lowercase()==userMail){
                    username.postValue(item.data["username"].toString())
                }
            }
        }


    }


    fun getUserPhoto(userMail: String, profileImage: MutableLiveData<String>){
        firestore.collection("User").get().addOnSuccessListener {
            for (item in it){
                if(item.data["email"].toString().lowercase()==userMail){
                    profileImage.postValue(item.data["downloadUri"].toString())

                }
            }
        }
    }
    fun getUserPhotoWithUsername(username: String, profileImage: MutableLiveData<String>){
        firestore.collection("User").get().addOnSuccessListener {
            for (item in it){
                if(item.data["username"].toString()==username){
                    profileImage.postValue(item.data["downloadUri"].toString())

                }
            }
        }
    }
    fun getComments(postId : String,commentList : MutableLiveData<ArrayList<Comment>>){
        var commentArrayList : ArrayList<Comment> = arrayListOf()
        firestore.collection("Post-Comments").orderBy("date", Query.Direction.DESCENDING).get().addOnSuccessListener {
            for(item in it){
                if(item.data["postId"]==postId){
                    commentArrayList.add(Comment(item.data["postId"].toString(),item.data["username"].toString(),item.data["comment"].toString()))
                }
            }
            commentList.postValue(commentArrayList)
        }
    }
    fun addComment(postId:String,username: String,comment: String,context: Context){
        val commentMap= hashMapOf<String,Any>()
        commentMap["postId"]=postId
        commentMap["username"]=username
        commentMap["comment"]=comment
        commentMap["date"]=com.google.firebase.Timestamp.now()


        firestore.collection("Post-Comments").add(commentMap).addOnSuccessListener {
            FancyToast.makeText(context,"Added comment!",
                FancyToast.LENGTH_LONG,
                FancyToast.SUCCESS,false).show()

        }.addOnFailureListener { exception ->
            FancyToast.makeText(context,exception.localizedMessage,
                FancyToast.LENGTH_LONG,
                FancyToast.ERROR,false).show()

        }

    }



}