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
import com.mustafaunlu.movieapp.R
import com.mustafaunlu.movieapp.databinding.FragmentLoginBinding
import com.mustafaunlu.movieapp.ui.activities.LoginActivity
import com.mustafaunlu.movieapp.ui.activities.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private var binding : FragmentLoginBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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
            println("Username: "+binding?.usernameEditText?.text)
            if(binding?.usernameEditText?.text?.isEmpty() == false && binding?.passwordEditText?.text?.isEmpty() == false){
                val intent=Intent(view.context,MainActivity::class.java);
                startActivity(intent)
            }else{
                Toast.makeText(context,"Fill the blanks!", Toast.LENGTH_LONG).show()
            }

        }
        binding?.newTextView?.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_signUpFragment)


        }


    }


}