package com.mustafaunlu.movieapp.ui.fragments.feed

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.navigation.fragment.findNavController
import com.mustafaunlu.movieapp.R
import com.mustafaunlu.movieapp.databinding.FragmentPostBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PostFragment : Fragment(), AdapterView.OnItemSelectedListener {

    private var binding : FragmentPostBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentPostBinding.inflate(inflater,container,false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var movieCategory= arrayOf("Science-Fiction","Adventure","Action")

        var spinner= binding!!.spinner
        spinner.onItemSelectedListener = this

        val arrayAdapter=ArrayAdapter(requireContext(),android.R.layout.simple_spinner_dropdown_item,movieCategory)
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter=arrayAdapter

        binding?.postButton?.setOnClickListener {
            findNavController().navigate(R.id.action_postFragment2_to_feedFragment)
        }


    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        TODO("Not yet implemented")
    }


}