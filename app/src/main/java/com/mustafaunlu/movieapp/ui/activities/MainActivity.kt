package com.mustafaunlu.movieapp.ui.activities
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mustafaunlu.movieapp.R
import com.mustafaunlu.movieapp.databinding.ActivityMainBinding
import com.mustafaunlu.movieapp.db.pref.SessionManager
import com.mustafaunlu.movieapp.ui.fragments.home.HomeFragment
import com.mustafaunlu.movieapp.ui.fragments.profile.ProfileFragment
import com.mustafaunlu.movieapp.ui.fragments.feed.FlowFragment
import com.mustafaunlu.movieapp.ui.fragments.home.MovieFragment
import com.mustafaunlu.movieapp.ui.fragments.profile.UserProfileFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var sessionManager: SessionManager

    private var binding : ActivityMainBinding? = null

    private  var movieFragment: MovieFragment = MovieFragment()
    private  var flowFragment: FlowFragment = FlowFragment()
    private var userProfile: UserProfileFragment = UserProfileFragment()
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
                R.id.home -> supportFragmentManager.beginTransaction().replace(R.id.fragmentView,movieFragment).commit()

                R.id.flow -> supportFragmentManager.beginTransaction().replace(R.id.fragmentView,flowFragment).commit()

                R.id.userProfile -> supportFragmentManager.beginTransaction().replace(R.id.fragmentView,userProfile).commit()

            }
        }

    }
}