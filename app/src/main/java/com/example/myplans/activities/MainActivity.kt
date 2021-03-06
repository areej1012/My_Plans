package com.example.myplans.activities

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.myplans.DB.Semester
import com.example.myplans.R
import com.example.myplans.databinding.ActivityMainBinding
import com.example.myplans.ui.calendar.CalendarFragment
import com.example.myplans.ui.plan.PlanFragment
import com.example.myplans.ui.planView.PlanViewFragment
import com.example.myplans.ui.setting.SettingFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var toolbar: Toolbar
    private lateinit var bottmNav: BottomNavigationView
    private lateinit var sharedPreferences: SharedPreferences
    private val viewModel by lazy { ViewModelProvider(this)[ViewModel::class.java] }

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
    private var isFirst = false
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
        //check is first time in app
        isFirst = readSharedPreferences()
        if (isFirst)
            alertSemester()

        loadFragment(PlanFragment())

        binding.includedContent.fabAdd.setOnClickListener {
            onAddButtonClick()
        }
        // add course button
        binding.includedContent.addCourse.setOnClickListener {
            startActivity(Intent(this, AddClassesActivity::class.java))
        }

        binding.includedContent.addMeeting.setOnClickListener {
            startActivity(Intent(this, AddMeetingActivity::class.java))
        }

        binding.includedContent.addHW.setOnClickListener {
            startActivity(Intent(this, AddHomeWorkActivity::class.java))
        }

    }

    private fun alertSemester() {
        // this is first time for using the app
        if (isFirst) {
            //open alert to take semester
            val dialog = Dialog(this)
            dialog.setContentView(R.layout.semester_alert)
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.setCancelable(false)
            val semesterNameLayout = dialog.findViewById<TextInputLayout>(R.id.etsemester)
            val semesterName = dialog.findViewById<TextInputEditText>(R.id.textInput)
            val saveSemester = dialog.findViewById<Button>(R.id.saveSemester)

            dialog.show()

            saveSemester.setOnClickListener {
                if (checkText(semesterName.text.toString())) {
                    semesterNameLayout.error = "Please fill in the semester name"
                } else {
                    // save semester in database
                    val calendar = Calendar.getInstance()
                    val dateFormat = SimpleDateFormat("yyyy-mm-dd")
                    val startDate = dateFormat.format(calendar.time)
                    calendar.add(Calendar.MONTH, 3)
                    val endDate = dateFormat.format(calendar.time)
                    val newSemester = Semester(semesterName.text.toString(), startDate, endDate)

                    viewModel.insertSemester(newSemester)
                    //save in sharedPreferences
                    isFirst = false
                    saveSharedPreferences(semesterName.text.toString())
                    //done
                    dialog.dismiss()
                }
            }
        }
    }

    //validation
    private fun checkText(text: String): Boolean {
        return text.isEmpty()
    }

    private fun onAddButtonClick() {
        setVisibility(closed)
        setAnimation(closed)
        closed = !closed
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
        return sharedPreferences.getBoolean("is_first_alert", true)
    }

    private fun saveSharedPreferences(semester: String) {
        with(sharedPreferences.edit()) {
            putBoolean("is_first_alert", isFirst)
            putString("semester", semester)
            apply()
        }
    }

    private fun loadFragment(fragment: Fragment) {
        // load fragment
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, fragment)
        transaction.commit()
    }

    override fun onStop() {
        super.onStop()
        onAddButtonClick()
    }
}