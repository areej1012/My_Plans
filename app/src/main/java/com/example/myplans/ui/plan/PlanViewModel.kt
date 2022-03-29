package com.example.myplans.ui.plan

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.myplans.DB.ClassStudent
import com.example.myplans.DB.Meeting
import com.example.myplans.DB.PlansDatabase
import com.example.myplans.DB.Repository
import java.util.*

class PlanViewModel(application: Application) : AndroidViewModel(application) {

    private var meetings: LiveData<List<Meeting>>
    private var listClass: LiveData<List<ClassStudent>>
    private val repository: Repository

    init {
        val planDao = PlansDatabase.getDatabase(application).plansDao()
        repository = Repository(planDao)
        val cel = Calendar.getInstance()
        val day = cel.get(Calendar.DAY_OF_MONTH)
        val month = cel.get(Calendar.MONTH)
        Log.d("PlanViewModel Month", month.toString())
        meetings = repository.getMeetings(day.toString(), month.toString())
        listClass = repository.getClassStudent()


    }

    fun getMeetings(): LiveData<List<Meeting>> {
        return meetings
    }

    fun getClass(): LiveData<List<ClassStudent>> {
        return listClass
    }

}