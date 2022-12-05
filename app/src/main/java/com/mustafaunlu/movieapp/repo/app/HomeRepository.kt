package com.mustafaunlu.movieapp.repo.app

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.type.Date
import com.google.type.DateTime
import com.mustafaunlu.movieapp.models.post.Comment
import com.mustafaunlu.movieapp.models.post.Post
import com.shashank.sony.fancytoastlib.FancyToast
import java.sql.Timestamp
import java.time.LocalDate
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



    fun likeMovie(userMail : String, imageUrl : String, title : String, overview : String, date : String, context : Context ){

        val likeMap= hashMapOf<String,Any>()
        likeMap["imageUrl"]=imageUrl
        likeMap["title"]=title
        likeMap["overview"]=overview
        likeMap["date"]=date


        firestore.collection("Liked-Movie").document(userMail).collection(title).add(likeMap).addOnSuccessListener {

            FancyToast.makeText(context,"Liked!",
                FancyToast.LENGTH_LONG,
                FancyToast.SUCCESS,false).show()

        }.addOnFailureListener { exception ->
            FancyToast.makeText(context,exception.localizedMessage,
                FancyToast.LENGTH_LONG,
                FancyToast.ERROR,false).show()

        }

    }

    fun postMovie(username: String,movName : String,category: String, postText : String,context: Context){
        val postMap= hashMapOf<String,Any>()
        val uuid=UUID.randomUUID()
        postMap["id"]=uuid.toString()
        postMap["movName"]=movName
        postMap["category"]=category
        postMap["postText"]=postText
        postMap["username"]=username
        postMap["date"]=com.google.firebase.Timestamp.now()

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

    fun getPost(callback : GetPostList): ArrayList<Post> {

        firestore.collection("Posts").orderBy("date", Query.Direction.DESCENDING).get().addOnSuccessListener {
            for (item in it){
                postList.add(Post(item.data["id"].toString(),item.data["username"].toString(),item.data["movName"].toString(),item.data["category"].toString(),item.data["postText"].toString()))
                callback.getPostList(postList)
            }
        }
        return postList
    }

    fun getSelectedPost(username: String, selectedPost : MutableLiveData<ArrayList<Post>>){
        var selectedPostArrayList : ArrayList<Post> = arrayListOf()
        firestore.collection("Posts").orderBy("date", Query.Direction.DESCENDING).get().addOnSuccessListener {
            for (item in it){
                if(item.data["username"].toString()==username){
                    selectedPostArrayList.add(Post(item.data["id"].toString(),item.data["username"].toString(),item.data["movName"].toString(),item.data["category"].toString(),item.data["postText"].toString()))
                }
            }
            selectedPost.postValue(selectedPostArrayList)
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
        println("home repo commment altı")
        var commentArrayList : ArrayList<Comment> = arrayListOf()
        firestore.collection("Post-Comments").orderBy("date", Query.Direction.DESCENDING).get().addOnSuccessListener {
            println("home repo collection altı")
            for(item in it){
                println("home repo item altı")
                if(item.data["postId"]==postId){
                    println("home post item altı")
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