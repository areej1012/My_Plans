package com.example.myplans.activities

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.example.myplans.R
import com.example.myplans.databinding.ActivityMainBinding
import com.example.myplans.ui.calendar.CalendarFragment
import com.example.myplans.ui.plan.PlanFragment
import com.example.myplans.ui.planView.PlanViewFragment
import com.example.myplans.ui.setting.SettingFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var toolbar: Toolbar
    private lateinit var bottmNav: BottomNavigationView
    private lateinit var sharedPreferences: SharedPreferences

    // creating variable that handles Animations loading
    // and initializing it with animation files that we have created
    private val rotateOpen: Animation by lazy {
        AnimationUtils.loadAnimation(
            this,
            R.anim.rotate_open_anim
        )
    }
    private val rotateClose: Animation by lazy {
        AnimationUtils.loadAnimation(
            this,
            R.anim.rotate_close_anim
        )
    }
    private val fromBottom: Animation by lazy {
        AnimationUtils.loadAnimation(
            this,
            R.anim.from_bottom_anim
        )
    }
    private val toBottom: Animation by lazy {
        AnimationUtils.loadAnimation(
            this,
            R.anim.to_bottom_anim
        )
    }

    //used to check if fab menu are opened or closed
    private var closed = false


    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        sharedPreferences = this.getSharedPreferences(
            getString(R.string.preference_file_key), Context.MODE_PRIVATE
        )

        bottmNav = binding.includedContent.bottomNav
        toolbar = binding.toolbar
        setSupportActionBar(toolbar)
        bottmNav.background = null
        loadFragment(PlanFragment())

        binding.includedContent.fabAdd.setOnClickListener {
            onAddButtonClick()
        }

    }

    private fun onAddButtonClick() {
        setVisibility(closed)
        setAnimation(closed)
        closed = !closed;
    }

    private fun setAnimation(closed: Boolean) {
        if (!closed) {
            binding.includedContent.menu.startAnimation(fromBottom)
            binding.includedContent.fabAdd.startAnimation(rotateOpen)
        } else {
            binding.includedContent.menu.startAnimation(toBottom)
            binding.includedContent.fabAdd.startAnimation(rotateClose)
        }
    }

    private fun setVisibility(closed: Boolean) {

        if (!closed) {
            binding.includedContent.menu.visibility = View.VISIBLE
        } else {
            binding.includedContent.menu.visibility = View.VISIBLE
        }
    }

    private fun bottomNavView() {
        bottmNav.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_plan -> {
                    loadFragment(PlanFragment())
                    toolbar.title = "Day"
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.navigation_plan_view -> {
                    loadFragment(PlanViewFragment())
                    toolbar.title = "Week"
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.navigation_calendar -> {
                    loadFragment(CalendarFragment())
                    toolbar.title = "Calender"
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.navigation_setting -> {
                    loadFragment(SettingFragment())
                    toolbar.title = "Setting"
                    return@setOnNavigationItemSelectedListener true
                }
            }
            return@setOnNavigationItemSelectedListener false
        }

    }

    private fun readSharedPreferences(): Boolean {
        return sharedPreferences.getBoolean("is_view", false)
    }

    private fun saveSharedPreferences() {
//        with(sharedPreferences.edit()) {
//            putBoolean("is_view", is_view)
//            apply()
//        }
    }

    private fun loadFragment(fragment: Fragment) {
        // load fragment
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, fragment)
        transaction.commit()
    }


}