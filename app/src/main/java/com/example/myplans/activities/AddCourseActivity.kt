package com.example.myplans.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.myplans.databinding.ActivityAddCourseBinding

class AddCourseActivity : AppCompatActivity() {
    lateinit var binding: ActivityAddCourseBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddCourseBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }



}