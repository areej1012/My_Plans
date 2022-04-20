package com.example.myplans.activities

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.myplans.DB.*
import com.example.myplans.DB.Relations.SemesterWithCourses
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class ViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: Repository
    private lateinit var semesterList: LiveData<List<SemesterWithCourses>>

    init {
        val plansDao = PlansDatabase.getDatabase(application).plansDao()
        repository = Repository(plansDao)

    }

    fun insertSemester(newSemester: Semester) {
        CoroutineScope(IO).launch {
            if (repository.insertSemester(newSemester) < 1)
                Log.e("Save", "Failed")
            else
                Log.e("Save", "Success")
        }

    }

    fun insertClass(newClass: ClassStudent) {
        CoroutineScope(IO).launch {
            repository.insertClass(newClass)
        }
    }

    fun insertHomeWork(newHomeWork: HomeWork): Long {
        var result: Long = 0
        CoroutineScope(IO).launch {
            result = repository.insertHomeWork(newHomeWork)
        }
        return result
    }


    fun getSemesterWithCourse(Semester: String): LiveData<List<SemesterWithCourses>> {
        return repository.getSemesterWithCourse(Semester)
    }
}