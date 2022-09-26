package com.mustafaunlu.movieapp.ui.fragments.login

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.mustafaunlu.movieapp.R
import com.mustafaunlu.movieapp.databinding.FragmentSignUpBinding
import com.mustafaunlu.movieapp.ui.activities.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpFragment : Fragment() {

    private var binding: FragmentSignUpBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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

                if(binding!!.passwordEditText.text.equals(binding!!.passwordOneEditText.text)){
                    //Intent to MainActivity
                    val intent=Intent(view.context,MainActivity::class.java);
                    startActivity(intent)
                    requireActivity().finish()

                }else{

                    Toast.makeText(context,"Passwords are not compatible!",Toast.LENGTH_LONG).show()
                }
            }else{
                Toast.makeText(context,"Fill the blanks!",Toast.LENGTH_LONG).show()

            }


        }
    }


}