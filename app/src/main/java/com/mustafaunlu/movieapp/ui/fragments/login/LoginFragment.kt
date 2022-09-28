package com.mustafaunlu.movieapp.ui.fragments.login

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.mustafaunlu.movieapp.R
import com.mustafaunlu.movieapp.databinding.FragmentLoginBinding
import com.mustafaunlu.movieapp.pref.SessionManager
import com.mustafaunlu.movieapp.ui.activities.LoginActivity
import com.mustafaunlu.movieapp.ui.activities.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private var binding : FragmentLoginBinding? = null

    private lateinit var mAuth: FirebaseAuth;


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mAuth=Firebase.auth;

        /*
        val currentUser=mAuth.currentUser
        if(currentUser != null){
            val intent=Intent(context,MainActivity::class.java);
            startActivity(intent)
            requireActivity().finish();
        }
        */

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=FragmentLoginBinding.inflate(inflater,container,false)
        return binding?.root;
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.signInButton?.setOnClickListener {
            if(binding?.loginEmailEditText?.text?.isEmpty() == false && binding?.loginPasswordEditText?.text?.isEmpty() == false){

                mAuth.signInWithEmailAndPassword(binding?.loginEmailEditText?.text.toString(),binding?.loginPasswordEditText?.text.toString()).addOnCompleteListener {  task ->
                    if(task.isSuccessful){
                        val intent=Intent(view.context,MainActivity::class.java);
                        startActivity(intent)
                        requireActivity().finish();
                    }

                }.addOnFailureListener {
                    Toast.makeText(context,it.toString(), Toast.LENGTH_LONG).show()

                }


            }else{
                Toast.makeText(context,"Fill the blanks!", Toast.LENGTH_LONG).show()
            }

        }
        binding?.newTextView?.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_signUpFragment)
        }
        binding?.forgotPasswordTextView?.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_introFragment)
        }


    }


}