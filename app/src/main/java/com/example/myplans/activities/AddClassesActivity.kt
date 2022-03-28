package com.example.myplans.activities

import android.app.Activity
import android.app.Dialog
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.example.myplans.DB.ClassStudent
import com.example.myplans.DB.PlansDatabase
import com.example.myplans.R
import com.example.myplans.databinding.ActivityAddClassesBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class AddClassesActivity : AppCompatActivity() {
    lateinit var binding: ActivityAddClassesBinding
    private var listNameCourse: MutableList<String> = mutableListOf()
    private val planDao by lazy { PlansDatabase.getDatabase(this).plansDao() }
    lateinit var sharedPreferences: SharedPreferences
    private val timerStartDialogListener: TimePickerDialog.OnTimeSetListener =
        TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute -> // logic to properly handle
            // the picked timings by user
            val formattedTime: String = when {
                hourOfDay == 0 -> {
                    if (minute < 10) {
                        if (minute == 0)
                            "${hourOfDay}:00 am"
                        "${hourOfDay + 12}:0${minute} am"
                    } else {
                        "${hourOfDay + 12}:${minute} am"
                    }
                }
                hourOfDay > 12 -> {
                    if (minute < 10) {
                        if (minute == 0)
                            "${hourOfDay}:00 pm"
                        "${hourOfDay - 12}:0${minute} pm"
                    } else {
                        "${hourOfDay - 12}:${minute} pm"
                    }
                }
                hourOfDay == 12 -> {
                    if (minute < 10) {
                        if (minute == 0)
                            "${hourOfDay}:00 pm"
                        "${hourOfDay}:0${minute} pm"
                    } else {
                        "${hourOfDay}:${minute} pm"
                    }
                }
                else -> {
                    if (minute < 10) {
                        if (minute == 0)
                            "${hourOfDay}:00 am"
                        "${hourOfDay}:${minute} am"
                    } else {
                        "${hourOfDay}:${minute} am"
                    }
                }
            }

            binding.btTimeStart.text = formattedTime
        }
    private val timerEndDialogListener: TimePickerDialog.OnTimeSetListener =
        TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute -> // logic to properly handle
            // the picked timings by user
            val formattedTime: String = when {
                hourOfDay == 0 -> {
                    if (minute < 10) {
                        if (minute == 0)
                            "${hourOfDay}:00 am"
                        "${hourOfDay + 12}:0${minute} am"
                    } else {
                        "${hourOfDay + 12}:${minute} am"
                    }
                }
                hourOfDay > 12 -> {
                    if (minute < 10) {
                        if (minute == 0)
                            "${hourOfDay}:00 pm"
                        "${hourOfDay - 12}:0${minute} pm"
                    } else {
                        "${hourOfDay - 12}:${minute} pm"
                    }
                }
                hourOfDay == 12 -> {
                    if (minute < 10) {
                        if (minute == 0)
                            "${hourOfDay}:00 pm"
                        "${hourOfDay}:0${minute} pm"
                    } else {
                        "${hourOfDay}:${minute} pm"
                    }
                }
                else -> {
                    if (minute < 10) {
                        if (minute == 0)
                            "${hourOfDay}:00 am"
                        "${hourOfDay}:${minute} am"
                    } else {
                        "${hourOfDay}:${minute} am"
                    }
                }
            }

            binding.btTimeEnd.text = formattedTime
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddClassesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        title = ""
        val toolbar = binding.toolbar
        setSupportActionBar(toolbar)


        binding.btCourse.setOnClickListener {
            showDialogCourse(this)
        }

        binding.btDate.setOnClickListener {
            showDialogDay(this)
        }

        binding.btTimeStart.setOnClickListener {
            val timePicker = TimePickerDialog(this, timerStartDialogListener, 8, 0, false)
            timePicker.show()
        }

        binding.btTimeEnd.setOnClickListener {
            val timePicker = TimePickerDialog(this, timerEndDialogListener, 10, 0, false)
            timePicker.show()
        }
    }


    override fun onStart() {
        super.onStart()

        sharedPreferences =
            this.getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE)
        val semester = sharedPreferences.getString("semester", "")
        CoroutineScope(IO).launch {
            val arraySemesterWithCourse = planDao.getSemesterWithCourse(semester!!)

            val listCourse = arraySemesterWithCourse[0].courses
            for (course in listCourse) {
                listNameCourse.add(course.nameCourse)
            }
        }

    }

    // for menu
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_meeting, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.add -> {
                validation()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun validation() {
        var isFillUp = false
        if (binding.btCourse.text.equals(resources.getString(R.string.choose_course))) {
            binding.tvErrorCourse.text = resources.getString(R.string.error_course)
            binding.tvErrorCourse.visibility = View.VISIBLE
            isFillUp = false
        } else {
            binding.tvErrorCourse.visibility = View.GONE
            isFillUp = true
        }

        if (binding.btDate.text.equals(resources.getString(R.string.choose_day))) {
            binding.tvErrorDay.text = resources.getString(R.string.error_day)
            binding.tvErrorDay.visibility = View.VISIBLE
            isFillUp = false
        } else {
            binding.tvErrorDay.visibility = View.GONE
            isFillUp = true
        }

        if (binding.btTimeStart.text.equals(resources.getString(R.string.choose_time))) {
            binding.tvErrorStart.text = resources.getString(R.string.error_time)
            binding.tvErrorStart.visibility = View.VISIBLE
            isFillUp = false
        } else {
            binding.tvErrorStart.visibility = View.GONE
            isFillUp = true
        }

        if (binding.btTimeEnd.text.equals(resources.getString(R.string.choose_time))) {
            binding.tvErrorEnd.text = resources.getString(R.string.error_time)
            binding.tvErrorEnd.visibility = View.VISIBLE
            isFillUp = false
        } else {
            binding.tvErrorEnd.visibility = View.GONE
            isFillUp = true
        }

        if (isFillUp) {
            saveDB()
        }

    }


    private fun showDialogCourse(activity: Activity) {
        val dialog = Dialog(activity)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.custom_dialog_class)

        // declare button in alert
        val btDialogAdd = dialog.findViewById(R.id.btAddCourse) as Button
        btDialogAdd.setOnClickListener {
            startActivity(Intent(this, AddCourseActivity::class.java))
            dialog.dismiss()
        }

        // declare list view
        val listView = dialog.findViewById<ListView>(R.id.ListView)
        val arrayAdapter =
            ArrayAdapter(this, R.layout.card_cell_add_course, R.id.tvCourse, listNameCourse)
        listView.adapter = arrayAdapter

        listView.setOnItemClickListener { _, _, position, _ ->
            binding.btCourse.text = listNameCourse[position]
            dialog.dismiss()
        }
        dialog.show()
    }

    private fun showDialogDay(activity: Activity) {
        val arrayDays = resources.getStringArray(R.array.array_days)
        val dialog = Dialog(activity)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.custom_dialog_days)

        val listView = dialog.findViewById<ListView>(R.id.ListViewDay)
        val arrayAdapter =
            ArrayAdapter(this, R.layout.card_cell_add_course, R.id.tvCourse, arrayDays)
        listView.adapter = arrayAdapter
        listView.setOnItemClickListener { _, _, position, _ ->
            binding.btDate.text = arrayDays[position].toString()
            Log.e("days", arrayDays[position].toString())
            dialog.dismiss()
        }
        dialog.show()
    }


    private fun saveDB() {
        val day = binding.btDate.text.toString().trim()
        val timeStart = binding.btTimeStart.text.toString().trim()
        val timeEnd = binding.btTimeEnd.text.toString().trim()
        val courseName = binding.btCourse.text.toString().trim()
        val newClass = ClassStudent(null, day, timeStart, timeEnd, courseName)

        CoroutineScope(IO).launch {
            if (planDao.insertClass(newClass) < 1) {
                Log.i("Save meeting", "Failed")
            } else {
                Log.e("Save course", "Success")
                finish()
            }

        }
    }

    override fun onPause() {
        super.onPause()
        listNameCourse = mutableListOf()
    }

}