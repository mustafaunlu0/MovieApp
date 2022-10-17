package com.mustafaunlu.movieapp.ui.activities
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.findNavController
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

    private  var homeFragment: HomeFragment = HomeFragment()
    private  var feedFragment: FeedFragment=FeedFragment()
    private var profileFragment: ProfileFragment=ProfileFragment()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)


        if(binding != null){
            setUpTabBar()
        }








    }

    private fun setUpTabBar()
    {



        //binding!!.bottomNavBar.setItemSelected(R.id.home,true)
        binding!!.bottomNavBar.setOnItemSelectedListener {
            when(it){
                R.id.home -> supportFragmentManager.beginTransaction().replace(R.id.fragmentView,homeFragment).commit()

                R.id.feed -> supportFragmentManager.beginTransaction().replace(R.id.fragmentView,feedFragment).commit()

                R.id.profile -> supportFragmentManager.beginTransaction().replace(R.id.fragmentView,profileFragment).commit()

            }
        }

    }
}