package com.example.myplans.ui.plan

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.myplans.DB.ClassStudent
import com.example.myplans.DB.Meeting
import com.example.myplans.DB.PlansDatabase
import com.example.myplans.DB.Repository
import java.text.SimpleDateFormat
import java.util.*

class PlanViewModel(application: Application) : AndroidViewModel(application) {

    private var meetings: LiveData<List<Meeting>>
    private var listClass: LiveData<List<ClassStudent>>
    private val repository: Repository

    init {
        val planDao = PlansDatabase.getDatabase(application).plansDao()
        repository = Repository(planDao)
        val cel = Calendar.getInstance()
        val dateFormat = SimpleDateFormat("dd-mm-yyyy")
        val timeNow = dateFormat.format(cel.time)
        meetings = repository.getMeetings(timeNow)
        listClass = repository.getClassStudent()


    }

    fun getMeetings(): LiveData<List<Meeting>> {
        return meetings
    }

    fun getClass(): LiveData<List<ClassStudent>> {
        return listClass
    }

}