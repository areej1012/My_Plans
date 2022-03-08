package com.example.myplans.activities

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.myplans.databinding.ActivityAddMeetingBinding
import java.util.*

class AddMeetingActivity : AppCompatActivity() {
    lateinit var binding: ActivityAddMeetingBinding
    lateinit var datePickerDialog: DatePickerDialog

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddMeetingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initDatePicker()

        binding.btDate.setOnClickListener {
            openPicker()
        }
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