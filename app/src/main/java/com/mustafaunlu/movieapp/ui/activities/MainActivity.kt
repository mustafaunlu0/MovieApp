package com.mustafaunlu.movieapp.ui.activities
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mustafaunlu.movieapp.R
import com.mustafaunlu.movieapp.databinding.ActivityMainBinding
import com.mustafaunlu.movieapp.db.pref.SessionManager
import com.mustafaunlu.movieapp.ui.fragments.home.FeedFragment
import com.mustafaunlu.movieapp.ui.fragments.home.HomeFragment
import com.mustafaunlu.movieapp.ui.fragments.home.ProfileFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var sessionManager: SessionManager

    private var binding : ActivityMainBinding? = null

    private lateinit var homeFragment: HomeFragment
    private lateinit var feedFragment: FeedFragment
    private lateinit var profileFragment: ProfileFragment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        homeFragment=HomeFragment()
        feedFragment=FeedFragment()
        profileFragment=ProfileFragment()

        if(binding != null){
            setUpTabBar()
        }

        println("Kaç kere girecek")






    }

    private fun setUpTabBar()
    {

        binding!!.bottomNavBar.setItemSelected(R.id.home,true)
        binding!!.bottomNavBar.setOnItemSelectedListener {
            when(it){
                R.id.home -> supportFragmentManager.beginTransaction().replace(R.id.mainFragmentContainerView,homeFragment).commit()

                R.id.feed -> supportFragmentManager.beginTransaction().replace(R.id.mainFragmentContainerView,feedFragment).commit()

                R.id.profile -> supportFragmentManager.beginTransaction().replace(R.id.mainFragmentContainerView,profileFragment).commit()

            }
        }

    }
}