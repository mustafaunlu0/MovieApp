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
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.mustafaunlu.movieapp.R
import com.mustafaunlu.movieapp.databinding.FragmentSignUpBinding
import com.mustafaunlu.movieapp.db.pref.SessionManager
import com.mustafaunlu.movieapp.viewmodel.LoginViewModel
import com.shashank.sony.fancytoastlib.FancyToast
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class SignUpFragment  : Fragment() {

    private var binding: FragmentSignUpBinding? = null

    private lateinit var activityResultLauncher: ActivityResultLauncher<Intent>
    private lateinit var permissionLauncher: ActivityResultLauncher<String>

    private var selectedPicture : Uri? = null

    private val viewModel : LoginViewModel by viewModels()

    @Inject
    lateinit var sessionManager: SessionManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        registerLauncher()
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

            val email=binding!!.emailEditText.text.toString()
            val username=binding!!.usernameEditText.text.toString()
            val password=binding!!.passwordEditText.text.toString()
            val passwordAgain=binding!!.passwordOneEditText.text.toString()

            if(username.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty() && passwordAgain.isNotEmpty()
                && selectedPicture != null){
                viewModel.signUp(email,username,password,passwordAgain, selectedPicture!!,context!!)

                    findNavController().navigate(R.id.action_signUpFragment_to_mainActivity)


            }else{
                FancyToast.makeText(requireContext(),"Fill the blanks !",
                    FancyToast.LENGTH_LONG,
                    FancyToast.CONFUSING,false).show();
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
                FancyToast.makeText(requireContext(),"Image not selected",FancyToast.LENGTH_LONG,FancyToast.INFO,false).show();

            }
        }

        permissionLauncher=registerForActivityResult(ActivityResultContracts.RequestPermission()){
            result ->
            if(result){
                val intent=Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                activityResultLauncher.launch(intent)
            }else{
                FancyToast.makeText(requireContext(),"Permission need2",FancyToast.LENGTH_LONG,FancyToast.INFO,false).show();
            }
        }
    }







}