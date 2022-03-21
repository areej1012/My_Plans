package com.example.myplans.activities

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.myplans.DB.Course
import com.example.myplans.DB.PlansDatabase
import com.example.myplans.R
import com.example.myplans.databinding.ActivityAddCourseBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class AddCourseActivity : AppCompatActivity() {
    lateinit var binding: ActivityAddCourseBinding
    private lateinit var sharedPreferences: SharedPreferences
    private val planDao by lazy { PlansDatabase.getDatabase(this).plansDao() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddCourseBinding.inflate(layoutInflater)
        setContentView(binding.root)
        title = ""
        val toolbar = binding.toolbar
        setSupportActionBar(toolbar)

        sharedPreferences = this.getSharedPreferences(
            getString(R.string.preference_file_key), Context.MODE_PRIVATE
        )


    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.add_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.addCourse -> {
                getText()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun getText() {
        val semester = sharedPreferences.getString("semester", "")
        Log.e("semester name", semester!!)
        val nameCourse = binding.editTextCourse.text.toString().trim()
        var description = binding.editTextDescription.text.toString().trim()
        if (nameCourse.isEmpty())
            binding.textLyNC.error = "Please fill up the Course name"
        else if (description.isEmpty()) {
            description = ""
            saveDB(semester, nameCourse, description)
        }

    }

    private fun saveDB(semester: String?, nameCourse: String, description: String) {
        val newCourse = Course(nameCourse, "", description, semester!!)

        CoroutineScope(IO).launch {
            if (planDao.insertCourse(newCourse) < 1)
                Log.e("Save meeting", "Failed")
            else {
                Log.e("Save meeting", "sucsse")
                finish()
            }
        }
    }


}