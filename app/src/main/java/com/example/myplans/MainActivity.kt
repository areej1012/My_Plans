package com.example.myplans

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
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
    var is_view = false
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

        bottomNavView()

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
        with(sharedPreferences.edit()) {
            putBoolean("is_view", is_view)
            apply()
        }
    }

    private fun loadFragment(fragment: Fragment) {
        // load fragment
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, fragment)
        transaction.commit()
    }
}