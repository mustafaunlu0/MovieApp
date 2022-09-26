package com.mustafaunlu.movieapp.ui.fragments.login

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.mustafaunlu.movieapp.R
import com.mustafaunlu.movieapp.databinding.FragmentSignUpBinding
import com.mustafaunlu.movieapp.ui.activities.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpFragment : Fragment() {

    private var binding: FragmentSignUpBinding? = null

    private lateinit var auth : FirebaseAuth;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth= Firebase.auth;

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


        binding?.signUpButton?.setOnClickListener {

            if(binding!!.usernameEditText.text.isNotEmpty() && binding!!.emailEditText.text.isNotEmpty() && binding!!.passwordEditText.text.isNotEmpty() && binding!!.passwordOneEditText.text.isNotEmpty()){

                println("1: "+binding!!.passwordEditText.text)
                println(": "+binding!!.passwordEditText.text)

                if(binding!!.passwordEditText.text.toString().equals(binding!!.passwordOneEditText.text.toString())){
                    //Sign up Firebase and Intent to MainActivity

                    auth.createUserWithEmailAndPassword(binding!!.emailEditText.text.toString(),binding!!.passwordEditText.text.toString()).addOnCompleteListener {  task ->
                        if(task.isSuccessful){
                            Toast.makeText(context,"Success",Toast.LENGTH_LONG).show()
                            val intent=Intent(view.context,MainActivity::class.java);
                            startActivity(intent)
                            requireActivity().finish()
                        }else{
                            Toast.makeText(context,"Failure",Toast.LENGTH_LONG).show()

                        }

                    }.addOnFailureListener {
                        Toast.makeText(context,it.toString(),Toast.LENGTH_LONG).show()

                    }



                }else{

                    Toast.makeText(context,"Passwords are not compatible!",Toast.LENGTH_LONG).show()
                }
            }else{
                Toast.makeText(context,"Fill the blanks!",Toast.LENGTH_LONG).show()

            }


        }
    }


}