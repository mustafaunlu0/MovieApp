package com.mustafaunlu.movieapp.ui.fragments.login
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.mustafaunlu.movieapp.R
import com.mustafaunlu.movieapp.databinding.FragmentLoginBinding
import com.mustafaunlu.movieapp.db.pref.SessionManager
import com.mustafaunlu.movieapp.ui.activities.MainActivity
import com.mustafaunlu.movieapp.viewmodel.LoginViewModel
import com.shashank.sony.fancytoastlib.FancyToast
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment : Fragment() {

    @Inject
    lateinit var sessionManager: SessionManager

    private var binding : FragmentLoginBinding? = null
    private val viewModel: LoginViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /*
        if(viewModel.isCurrentUser(requireContext())){
            val intent=Intent(context, MainActivity::class.java);
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
            val email=binding!!.loginEmailEditText.text.toString()
            val password=binding!!.loginPasswordEditText.text.toString()
            if (email.isNotEmpty() && password.isNotEmpty()) {
                viewModel.signIn(email, password, requireContext())
                    findNavController().navigate(R.id.action_loginFragment_to_mainActivity)

            }else{
                FancyToast.makeText(requireContext(),"Fill the blanks !",FancyToast.LENGTH_LONG,FancyToast.CONFUSING,false).show();


            }

        }
        binding?.newTextView?.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_signUpFragment)
        }
        binding!!.forgotPasswordTextView.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_postFragment)
        }


    }


}