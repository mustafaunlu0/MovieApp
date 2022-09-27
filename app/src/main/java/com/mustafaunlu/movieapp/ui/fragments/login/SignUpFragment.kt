package com.mustafaunlu.movieapp.ui.fragments.login

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import com.mustafaunlu.movieapp.databinding.FragmentSignUpBinding
import com.mustafaunlu.movieapp.ui.activities.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import java.sql.Timestamp
import java.util.*
import java.util.jar.Manifest

@AndroidEntryPoint
class SignUpFragment : Fragment() {

    private var binding: FragmentSignUpBinding? = null

    private lateinit var auth : FirebaseAuth;
    private lateinit var storage : FirebaseStorage
    private lateinit var firestore : FirebaseFirestore

    private lateinit var activityResultLauncher: ActivityResultLauncher<Intent>
    private lateinit var permissionLauncher: ActivityResultLauncher<String>

    private var selectedPicture : Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        registerLauncher()
        auth= Firebase.auth
        storage = Firebase.storage
        firestore=Firebase.firestore


    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentSignUpBinding.inflate(inflater,container,false);
        return binding?.root;
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

       binding!!.profileImageView.setOnClickListener{
           selectImage()
       }

        binding!!.signUpButton.setOnClickListener {
            if(binding!!.usernameEditText.text.isNotEmpty() && binding!!.emailEditText.text.isNotEmpty() && binding!!.passwordEditText.text.isNotEmpty() && binding!!.passwordOneEditText.text.isNotEmpty()){
                createUser()

            }else{
                Toast.makeText(context,"Fill the blanks!",Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun selectImage() {
        if(ContextCompat.checkSelfPermission(context!!,android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){

            if(ActivityCompat.shouldShowRequestPermissionRationale(activity!!,android.Manifest.permission.READ_EXTERNAL_STORAGE)){
                Snackbar.make(view!!,"Permission needed for gallery",Snackbar.LENGTH_INDEFINITE).setAction("Give permission", View.OnClickListener {
                    //request permission
                    permissionLauncher.launch(android.Manifest.permission.READ_EXTERNAL_STORAGE)
                }).show()
            }else{
                //request permission
                permissionLauncher.launch(android.Manifest.permission.READ_EXTERNAL_STORAGE)
            }

        }else{
            //permission granted
            val intentToGallery=Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            activityResultLauncher.launch(intentToGallery)
        }
    }
    private fun registerLauncher() {

        activityResultLauncher=registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            result ->
            if(result.resultCode == RESULT_OK){
                val intentFromData=result.data
                if(intentFromData != null){
                    selectedPicture=intentFromData.data
                    selectedPicture?.let {
                        binding!!.profileImageView.setImageURI(it)
                    }
                }

            }else{
                Toast.makeText(context,"Permission need",Toast.LENGTH_LONG).show()
            }
        }

        permissionLauncher=registerForActivityResult(ActivityResultContracts.RequestPermission()){
            result ->
            if(result){
                val intent=Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                activityResultLauncher.launch(intent)
            }else{
                Toast.makeText(context,"Permission needed",Toast.LENGTH_LONG).show()
            }
        }
    }


    private fun createUser(){
        if(binding!!.passwordEditText.text.toString().equals(binding!!.passwordOneEditText.text.toString())){
            //Sign up Firebase and Intent to MainActivity

            auth.createUserWithEmailAndPassword(binding!!.emailEditText.text.toString(),binding!!.passwordEditText.text.toString()).addOnCompleteListener {  task ->
                if(task.isSuccessful){
                    Toast.makeText(context,"Success",Toast.LENGTH_LONG).show()
                    createUserWithInfo()

                }else{
                    Toast.makeText(context,"Failure",Toast.LENGTH_LONG).show()

                }

            }.addOnFailureListener {
                Toast.makeText(context,it.toString(),Toast.LENGTH_LONG).show()

            }



        }else{
            Toast.makeText(context,"Passwords are not compatible!",Toast.LENGTH_LONG).show()
        }
    }

    private fun createUserWithInfo() {
        println("fonksiyon başı")
        val uuid = UUID.randomUUID()
        val imageName="$uuid.jpg"
        println("imageName: "+imageName)

        val reference= storage.reference
        val imageReference=reference.child("images").child(imageName)

        //önce storage e koy sonra uriyi al firestore a at
        if(selectedPicture != null){
            println("selectedPicture: "+selectedPicture)

            imageReference.putFile(selectedPicture!!).addOnSuccessListener {

                val uploadPictureReference=storage.reference.child("images").child(imageName)

                uploadPictureReference.downloadUrl.addOnSuccessListener {
                    val downloadUri=it.toString()
                    if(auth.currentUser != null){
                        println("DownloadUri: "+downloadUri)

                        val postMap= hashMapOf<String,Any>()
                        postMap["downloadUri"] = downloadUri
                        postMap["email"]=auth.currentUser!!.email!!
                        postMap["username"]=binding!!.usernameEditText.text.toString()
                        postMap["password"]=binding!!.passwordEditText.text.toString()
                        postMap["date"]=com.google.firebase.Timestamp.now()
                        println("collectionUstu")
                        firestore.collection("User").add(postMap).addOnSuccessListener {
                            println("collectionAltı")
                            Toast.makeText(context,"Successful Login!",Toast.LENGTH_LONG).show()
                            val intent=Intent(context,MainActivity::class.java);
                            startActivity(intent)
                            requireActivity().finish()
                        }.addOnFailureListener { exception->
                            Toast.makeText(context,exception.localizedMessage,Toast.LENGTH_LONG).show()
                        }

                    }



                }.addOnFailureListener { exception->
                    Toast.makeText(context,exception.localizedMessage,Toast.LENGTH_LONG).show()
                }
            }.addOnFailureListener{ exception ->
                Toast.makeText(context,exception.localizedMessage,Toast.LENGTH_LONG).show()

            }
        }


    }


}