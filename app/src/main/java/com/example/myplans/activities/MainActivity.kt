package com.example.myplans.activities

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.example.myplans.DB.PlansDatabase
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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var toolbar: Toolbar
    private lateinit var bottmNav: BottomNavigationView
    private lateinit var sharedPreferences: SharedPreferences
    private val planDoa by lazy {
        PlansDatabase.getDatabase(this).plansDao()
    }

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

    }

    private fun alertSemester() {
        if (isFirst) {
            val dialog = Dialog(this)
            dialog.setContentView(R.layout.semester_alert)
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.setCancelable(false)
            val semesterNameLayout = dialog.findViewById<TextInputLayout>(R.id.etsemester)
            val semeseterName = dialog.findViewById<TextInputEditText>(R.id.textInput)
            val saveSemester = dialog.findViewById<Button>(R.id.saveSemester)

            dialog.show()

            saveSemester.setOnClickListener {
                if (checkText(semeseterName.text.toString())) {
                    semesterNameLayout.error = "Please fill in the semester name"
                } else {
                    // save name in database
                    val calendar = Calendar.getInstance()
                    val dateFormat = SimpleDateFormat("yyyy-mm-dd")
                    val startDate = dateFormat.format(calendar.time)
                    calendar.add(Calendar.MONTH, 3)
                    val endDate = dateFormat.format(calendar.time)
                    val newSemester = Semester(semeseterName.text.toString(), startDate, endDate)

                    CoroutineScope(IO).launch {
                        if (planDoa.insertSemester(newSemester) < 1)
                            Log.e("Save", "Faild")
                        else
                            Log.e("Save", "work")
                    }

                    //save in sharedPreferences
                    isFirst = false
                    saveSharedPreferences(saveSemester.text.toString())
                    //done
                    dialog.dismiss()
                }
            }
        }
    }

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
                    Toast.makeText(this, "Clendar", Toast.LENGTH_SHORT).show()
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