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
import androidx.lifecycle.ViewModelProvider
import com.example.myplans.DB.ClassStudent
import com.example.myplans.R
import com.example.myplans.databinding.ActivityAddClassesBinding

class AddClassesActivity : AppCompatActivity() {
    lateinit var binding: ActivityAddClassesBinding
    private var listNameCourse: MutableList<String> = mutableListOf()
    private val viewModel by lazy { ViewModelProvider(this)[ViewModel::class.java] }
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
                        "${hourOfDay}:0${minute} am"
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
                        "${hourOfDay}:0${minute} am"
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
        val toolbar = binding.toolbar

        setSupportActionBar(toolbar)



        binding.btCourse.setOnClickListener {
            showDialogCourse(this)
        }

        binding.btDate.setOnClickListener {
            showDialogDay(this)
        }

        binding.btTimeStart.setOnClickListener {
            val timePicker =
                TimePickerDialog(this, R.style.Theme_Dialog, timerStartDialogListener, 8, 0, false)
            timePicker.show()
        }

        binding.btTimeEnd.setOnClickListener {
            val timePicker =
                TimePickerDialog(this, R.style.Theme_Dialog, timerEndDialogListener, 10, 0, false)
            timePicker.show()
        }

        retrieveCourse()
    }


    private fun retrieveCourse() {
        sharedPreferences =
            this.getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE)
        val semester = sharedPreferences.getString("semester", "")

        val arraySemesterWithCourse = viewModel.getSemesterWithCourse(semester!!)

        arraySemesterWithCourse.observe(this, { semesterList ->
            val courseList = semesterList[0].courses
            for (course in courseList) {
                listNameCourse.add(course.nameCourse)
            }
        })
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
        val day = daysInEn(binding.btDate.text.toString().trim())
        val timeStart = binding.btTimeStart.text.toString().trim()
        val timeEnd = binding.btTimeEnd.text.toString().trim()
        val courseName = binding.btCourse.text.toString().trim()
        val newClass = ClassStudent(null, day, timeStart, timeEnd, courseName)

        viewModel.insertClass(newClass)
        finish()


    }

    private fun daysInEn(day: String): String {
        when (day) {
            "الأحد" -> return "sunday"
            "الأثنين" -> return "monday"
            "الثلاثاء" -> return "tuesday"
            "الأربعاء" -> return "wednesday"
            "الخميس" -> return "thursday"
            "الجمعة" -> return "friday"
            "السبت" -> return "saturday"
        }
        return day
    }

    override fun onPause() {
        super.onPause()
        listNameCourse = mutableListOf()
    }

}