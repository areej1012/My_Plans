package com.example.myplans.activities

import android.app.Activity
import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.myplans.DB.HomeWork
import com.example.myplans.DB.PlansDatabase
import com.example.myplans.R
import com.example.myplans.databinding.ActivityAddHomeWorkBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import java.util.*

class AddHomeWorkActivity : AppCompatActivity() {
    lateinit var binding: ActivityAddHomeWorkBinding
    private var listNameCourse: MutableList<String> = mutableListOf()
    private val planDao by lazy { PlansDatabase.getDatabase(this).plansDao() }
    lateinit var datePickerDialog: DatePickerDialog
    lateinit var datePickerDialogReminder: DatePickerDialog
    lateinit var sharedPreferences: SharedPreferences
    private var day = ""
    private var month = ""
    private var year = ""
    private var dayReminder = ""
    private var monthReminder = ""
    private var yearReminder = ""

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddHomeWorkBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val toolbar = binding.toolbar
        setSupportActionBar(toolbar)

        initDatePicker()
        initDatePickerReminder()

        binding.btCourse.setOnClickListener {
            showDialogCourse(this)
        }

        binding.btDate.setOnClickListener {
            openPicker()
        }

    }

    override fun onStart() {
        super.onStart()
        sharedPreferences =
            this.getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE)
        val semester = sharedPreferences.getString("semester", "")
        CoroutineScope(Dispatchers.IO).launch {
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

    @RequiresApi(Build.VERSION_CODES.N)
    private fun initDatePickerReminder() {
        val dateListenerReminder =
            DatePickerDialog.OnDateSetListener { datePicker, year, month, day ->
                val date = makeDateString(day, month, year)
                binding.btDate.text = date
                this.dayReminder = "$day"
                this.monthReminder = "$month"
                this.yearReminder = "$year"
            }

        val cel = Calendar.getInstance()
        val year = cel.get(Calendar.YEAR)
        val month = cel.get(Calendar.MONTH)
        val day = cel.get(Calendar.DAY_OF_MONTH)

        datePickerDialog =
            DatePickerDialog(this, R.style.Theme_Dialog, dateListenerReminder, year, month, day)
    }

    private fun validation() {
        if (binding.editTextCourse.text.toString().isEmpty())
            binding.textLyNTitle.error = resources.getString(R.string.fill_title)
        else if (binding.btCourse.text.toString()
                .equals(resources.getString(R.string.choose_course))
        )
            binding.tvErrorCourse.text = resources.getText(R.string.error_course)
        else if (binding.btDate.text.toString().equals(resources.getString(R.string.choose_date)))
            binding.tvErrorDate.text = resources.getString(R.string.error_date)
        else
            saveDB()
    }

    private fun saveDB() {
        val title = binding.editTextCourse.text.toString().trim()
        val desc = binding.editTextDec.text.toString().trim()
        val course = binding.btCourse.text.toString()
        val newHomeWork = HomeWork(
            null,
            title,
            desc,
            day,
            month,
            year,
            dayReminder,
            monthReminder,
            yearReminder,
            course
        )

        CoroutineScope(IO).launch {
            if (planDao.insertHomeWork(newHomeWork) < 1)
                Log.i("Save meeting", "Failed")
            else {
                Log.e("Save course", "Success")
                finish()
            }
        }
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

    private fun openPicker() {
        datePickerDialog.show()
    }

    private fun openPickerReminder() {
        datePickerDialogReminder.show()
    }
}