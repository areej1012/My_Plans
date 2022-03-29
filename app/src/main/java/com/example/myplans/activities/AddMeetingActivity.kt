package com.example.myplans.activities

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.myplans.DB.Meeting
import com.example.myplans.DB.PlansDatabase
import com.example.myplans.R
import com.example.myplans.databinding.ActivityAddMeetingBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import java.util.*

class AddMeetingActivity : AppCompatActivity() {
    lateinit var binding: ActivityAddMeetingBinding
    lateinit var datePickerDialog: DatePickerDialog
    private val planDao by lazy { PlansDatabase.getDatabase(this).plansDao() }
    private var day = ""
    private var month = ""
    private var year = ""

    // listener which is triggered when the
    // time is picked from the time picker dialog
    private val timePickerDialogListener: TimePickerDialog.OnTimeSetListener =
        TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute -> // logic to properly handle
            // the picked timings by user
            val formattedTime: String = when {
                hourOfDay == 0 -> {
                    if (minute < 10) {
                        "${hourOfDay + 12}:0${minute} am"
                    } else {
                        "${hourOfDay + 12}:${minute} am"
                    }
                }
                hourOfDay > 12 -> {
                    if (minute < 10) {
                        "${hourOfDay - 12}:0${minute} pm"
                    } else {
                        "${hourOfDay - 12}:${minute} pm"
                    }
                }
                hourOfDay == 12 -> {
                    if (minute < 10) {
                        "${hourOfDay}:0${minute} pm"
                    } else {
                        "${hourOfDay}:${minute} pm"
                    }
                }
                else -> {
                    if (minute < 10) {
                        "${hourOfDay}:${minute} am"
                    } else {
                        "${hourOfDay}:${minute} am"
                    }
                }
            }

            binding.btTime.text = formattedTime
        }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddMeetingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        title = ""
        val toolbar = binding.toolbar
        setSupportActionBar(toolbar)
        initDatePicker()

        binding.btTime.setOnClickListener {
            val timePicker =
                TimePickerDialog(this, R.style.Theme_Dialog, timePickerDialogListener, 12, 0, false)
            timePicker.show()
        }
        binding.btDate.setOnClickListener {
            openPicker()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_meeting, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.add -> {
                saveInDB()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun saveInDB() {
        if (binding.tvTitle.text?.isEmpty() == true)
            binding.textLyNC.error = "Please fill up the title"
        else {
            val time = binding.btTime.text.toString()
            val location = binding.tvLocation.text.toString()
            val newMeeting =
                Meeting(null, binding.tvTitle.text.toString(), day, month, year, time, location)
            CoroutineScope(IO).launch {
                if (planDao.insertMeeting(newMeeting) < 1)
                    Log.e("Save meeting", "Failed")
                else
                    Log.e("Save meeting", "Success")
                finish()
            }

        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun initDatePicker() {
        val dateListener = DatePickerDialog.OnDateSetListener { datePicker, year, month, day ->
            val date = makeDateString(day, month, year)
            binding.btDate.text = date
            this.day = "$day"
            this.month = "$month"
            this.year = "$year"
        }

        val cel = Calendar.getInstance()
        val year = cel.get(Calendar.YEAR)
        val month = cel.get(Calendar.MONTH)
        val day = cel.get(Calendar.DAY_OF_MONTH)

        datePickerDialog =
            DatePickerDialog(this, R.style.Theme_Dialog, dateListener, year, month, day)


    }

    private fun makeDateString(day: Int, month: Int, year: Int): String {
        return "$day - " + getMonthFormat(month) + "- $year"
    }

    private fun getMonthFormat(month: Int): String {
        when (month) {
            0 -> return "Jan"
            1 -> return "Feb"
            2 -> return "Mar"
            3 -> return "Apr"
            4 -> return "May"
            5 -> return "June"
            6 -> return "July"
            7 -> return "Aug"
            8 -> return "Sept"
            9 -> return "Oct"
            10 -> return "Nov"
            11 -> return "Dec"
        }
        return "Jan"
    }

    private fun openPicker() {
        datePickerDialog.show()
    }
}