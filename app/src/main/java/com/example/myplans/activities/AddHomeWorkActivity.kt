package com.example.myplans.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.myplans.databinding.ActivityAddHomeWorkBinding

class AddHomeWorkActivity : AppCompatActivity() {
    lateinit var binding: ActivityAddHomeWorkBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddHomeWorkBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}