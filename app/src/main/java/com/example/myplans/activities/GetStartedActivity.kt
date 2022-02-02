package com.example.myplans.activities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.myplans.R
import com.example.myplans.databinding.ActivityGetStartedBinding

class GetStartedActivity : AppCompatActivity() {
    lateinit var binding: ActivityGetStartedBinding
    private lateinit var sharedPreferences: SharedPreferences
    private var isFirst: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGetStartedBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = this.getSharedPreferences(
            getString(R.string.preference_file_key), Context.MODE_PRIVATE
        )
        checkIsFirstTime()

        binding.getStarted.setOnClickListener {
            moveToNextActivity()
        }

    }

    private fun checkIsFirstTime() {
        isFirst = sharedPreferences.getBoolean("is_first", true)
        if (!isFirst) {
            moveToNextActivity()
        } else {
            with(sharedPreferences.edit()) {
                putBoolean("is_first", false)
                apply()
            }
        }
    }

    private fun moveToNextActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}