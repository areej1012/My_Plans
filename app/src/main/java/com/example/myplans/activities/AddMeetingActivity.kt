package com.example.myplans.activities

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.TimePicker
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.myplans.R
import com.example.myplans.databinding.ActivityAddMeetingBinding
import java.util.*

class AddMeetingActivity : AppCompatActivity() {
    lateinit var binding: ActivityAddMeetingBinding
    lateinit var datePickerDialog: DatePickerDialog

    // listener which is triggered when the
    // time is picked from the time picker dialog
    private val timePickerDialogListener: TimePickerDialog.OnTimeSetListener =
        object : TimePickerDialog.OnTimeSetListener {
            override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {

                // logic to properly handle
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
            val timePicker = TimePickerDialog(this, timePickerDialogListener, 12, 10, false)
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
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun initDatePicker() {
        val dateListener = DatePickerDialog.OnDateSetListener { datePicker, year, month, day ->
            val date = makeDateString(day, month, year)
            binding.btDate.text = date
            Log.e("day", getMonthFormat(month))
        }

        val cel = Calendar.getInstance()
        val year = cel.get(Calendar.YEAR)
        val month = cel.get(Calendar.MONTH)
        val day = cel.get(Calendar.DAY_OF_MONTH)
        val style = AlertDialog.THEME_HOLO_LIGHT

        datePickerDialog = DatePickerDialog(this, style, dateListener, year, month, day)


    }

    private fun makeDateString(day: Int, month: Int, year: Int): String {
        return "$day - " + getMonthFormat(month) + "- $year"
    }

    private fun getMonthFormat(month: Int): String {
        when (month) {
            1 -> return "Jan"
            2 -> return "Feb"
            3 -> return "Mar"
            4 -> return "Apr"
            5 -> return "May"
            6 -> return "June"
            7 -> return "July"
            8 -> return "Aug"
            9 -> return "Sept"
            10 -> return "Oct"
            11 -> return "Nov"
            12 -> return "Dec"
        }
        return "Jan"
    }

    private fun openPicker() {
        datePickerDialog.show()
    }
}