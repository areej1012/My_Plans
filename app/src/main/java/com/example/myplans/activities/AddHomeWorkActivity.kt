package com.example.myplans.activities

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.example.myplans.DB.PlansDatabase
import com.example.myplans.R
import com.example.myplans.databinding.ActivityAddHomeWorkBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddHomeWorkActivity : AppCompatActivity() {
    lateinit var binding: ActivityAddHomeWorkBinding
    private var listNameCourse: MutableList<String> = mutableListOf()
    private val planDao by lazy { PlansDatabase.getDatabase(this).plansDao() }
    lateinit var sharedPreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddHomeWorkBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val toolbar = binding.toolbar
        setSupportActionBar(toolbar)

        binding.btCourse.setOnClickListener {
            showDialogCourse(this)
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

    private fun validation() {

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


}