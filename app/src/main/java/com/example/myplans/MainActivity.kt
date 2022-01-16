package com.example.myplans

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.Menu
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.example.myplans.databinding.ActivityMainBinding
import com.example.myplans.ui.calendar.CalendarFragment
import com.example.myplans.ui.plan.PlanFragment
import com.example.myplans.ui.plan.PlanViewModel
import com.example.myplans.ui.planView.PlanViewFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var bottmNav : BottomNavigationView
    private lateinit var sharedPreferences: SharedPreferences
    var is_view = false
lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        sharedPreferences = this.getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE
        )

        bottmNav = binding.included.includedContent.bottomNav
        val toolbar: Toolbar = binding.included.toolbar
        setSupportActionBar(toolbar)
        bottmNav.background = null
        is_view = readSharedPreferences()

        if(is_view)
            loadFragment(PlanViewFragment())
        else
            loadFragment(PlanFragment())
        bottomNavView()

    }

    private fun bottomNavView() {
        bottmNav.setOnNavigationItemSelectedListener  { item ->
                when(item.itemId){
                    R.id.navigation_plan-> {
                        loadFragment(PlanFragment())
                       return@setOnNavigationItemSelectedListener true
                    }
                    R.id.navigation_calendar -> {
                        loadFragment(CalendarFragment())
                        return@setOnNavigationItemSelectedListener true
                    }
                }
            return@setOnNavigationItemSelectedListener false
            }

        }

    private fun readSharedPreferences() : Boolean{
        return  sharedPreferences.getBoolean("is_view",false)
    }

    private fun saveSharedPreferences(){
        with(sharedPreferences.edit()) {
            putBoolean("is_view", is_view)
            apply()
        }
    }

    private fun loadFragment(fragment: Fragment) {
        // load fragment
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

//    override fun onSupportNavigateUp(): Boolean {
//        val navController = findNavController(R.id.nav_host_fragment)
//        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
//    }
}