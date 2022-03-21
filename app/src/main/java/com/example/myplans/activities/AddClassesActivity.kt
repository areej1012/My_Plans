package com.example.myplans.activities

import android.app.Activity
import android.app.Dialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.example.myplans.R
import com.example.myplans.databinding.ActivityAddClassesBinding

class AddClassesActivity : AppCompatActivity() {
    lateinit var binding: ActivityAddClassesBinding
    var arrayCourse = arrayListOf<String>("Math", "Champers")
    private val timerStartDialogListener: TimePickerDialog.OnTimeSetListener =
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

            binding.btTimeStart.text = formattedTime
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddClassesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        title = ""
        val toolbar = binding.toolbar
        setSupportActionBar(toolbar)


        binding.btCourse.setOnClickListener {
            showDialog(this)
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
                saveDB()
            }
        }
        return super.onOptionsItemSelected(item)
    }


    private fun showDialog(activity: Activity) {
        val dialog = Dialog(activity)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.custom_dialog_class)

        // declare button in alert
        val btDialogAdd = dialog.findViewById(R.id.btAddCourse) as Button
        btDialogAdd.setOnClickListener {
            startActivity(Intent(this, AddCourseActivity::class.java))
        }

        // declare list view
        val listView = dialog.findViewById<ListView>(R.id.ListView)
        val arrayAdapter =
            ArrayAdapter(this, R.layout.card_cell_add_course, R.id.tvCourse, arrayCourse)
        listView.adapter = arrayAdapter

        listView.setOnItemClickListener { _, _, position, _ ->
            binding.btCourse.text = arrayCourse[position]
            dialog.dismiss()
        }
        dialog.show()
    }


    private fun saveDB() {


    }


}