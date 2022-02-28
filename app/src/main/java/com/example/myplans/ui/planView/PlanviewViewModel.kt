package com.example.myplans.ui.planView

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PlanviewViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Plan view"
    }
    val text: LiveData<String> = _text
}