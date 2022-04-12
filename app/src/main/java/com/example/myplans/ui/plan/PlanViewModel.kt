package com.example.myplans.ui.plan

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.myplans.DB.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class PlanViewModel(application: Application) : AndroidViewModel(application) {

    private var meetings: LiveData<List<Meeting>>
    private var listClass: LiveData<List<ClassStudent>>
    private val listHomeWorks: LiveData<List<HomeWork>>
    private val repository: Repository

    init {
        val planDao = PlansDatabase.getDatabase(application).plansDao()
        repository = Repository(planDao)
        val cel = Calendar.getInstance()
        val day = cel.get(Calendar.DAY_OF_MONTH)
        val month = cel.get(Calendar.MONTH)
        meetings = repository.getMeetings(day.toString(), month.toString())
        listClass = repository.getClassStudent()
        listHomeWorks = repository.getHomeWork(day.toString(), month.toString())


    }

    fun getMeetings(): LiveData<List<Meeting>> {
        return meetings
    }

    fun getClass(): LiveData<List<ClassStudent>> {
        return listClass
    }

    fun getHomeWork(): LiveData<List<HomeWork>> {
        return listHomeWorks
    }

    fun deleteNote(classStudent: ClassStudent) {
        CoroutineScope(Dispatchers.IO).launch {
            repository.deleteClass(classStudent)
        }
    }

}