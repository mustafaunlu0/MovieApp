package com.mustafaunlu.movieapp.ui.fragments.feed

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.mustafaunlu.movieapp.R
import com.mustafaunlu.movieapp.databinding.FragmentPostBinding
import com.mustafaunlu.movieapp.viewmodel.GenreViewModel
import com.mustafaunlu.movieapp.viewmodel.HomeViewModel
import com.mustafaunlu.movieapp.viewmodel.MovieViewModel
import com.shashank.sony.fancytoastlib.FancyToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import kotlin.random.Random

@AndroidEntryPoint
class PostFragment : Fragment(), AdapterView.OnItemSelectedListener {

    private val movieViewModel : MovieViewModel by viewModels()
    private val genreViewModel : GenreViewModel by viewModels()
    private val viewModel : HomeViewModel by viewModels()
    private var binding : FragmentPostBinding? = null
    var movieCategory= ArrayList<String>()
    private var category :String= ""
    private var isCategoryClicked : Boolean =false



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


        movieCategory.add("Select Category")

        for( item in genreViewModel.readAllGenres()){
            movieCategory.add(item.genre_name_en)
        }
        //Problem
        //Kullanıcı adını alma problemi yaşıyoruz çekerken zorlanıyor
        movieViewModel.findUserName(movieViewModel.getCurrentUserEmail())
        movieViewModel.getUsername().observe(viewLifecycleOwner){
            println("burada ne var: $it")


        }

        binding?.postButton?.setOnClickListener {
            if(category !="Select Category"){

                //movieViewModel.findUserName(movieViewModel.getCurrentUserEmail())
                println("okey")
                movieViewModel.getUsername().observe(viewLifecycleOwner){
                    println("burada : $it")
                    movieViewModel.postMovie(it,binding!!.movNameEditText.text.toString(),category,binding!!.postEditText.text.toString(),requireContext())
                    findNavController().navigate(R.id.action_postFragment2_to_feedFragment)

                }
                println("okey")



            }else{
                FancyToast.makeText(requireContext(),"Do not select category",
                    FancyToast.LENGTH_LONG,
                    FancyToast.ERROR,false).show()
            }


        }



        var spinner= binding!!.spinner
        spinner.onItemSelectedListener = this

        val arrayAdapter=ArrayAdapter(requireContext(),android.R.layout.simple_spinner_dropdown_item,movieCategory)
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter=arrayAdapter


        fetchMovies()


    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        println("ne geldi : "+p0?.getItemAtPosition(p2).toString())
        category=p0?.getItemAtPosition(p2).toString()




    }

    override fun onNothingSelected(p0: AdapterView<*>?) {

    }
    private fun fetchMovies() {
        CoroutineScope(Dispatchers.IO).launch {
            val random = Random.nextInt(0, 5)
            val job1: Deferred<Unit> = async {
                movieViewModel.findUserName(movieViewModel.getCurrentUserEmail())
            }


            job1.await()


        }
    }


}