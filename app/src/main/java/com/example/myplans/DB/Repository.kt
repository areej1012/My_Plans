package com.example.myplans.DB

import androidx.lifecycle.LiveData
import com.example.myplans.DB.Relations.SemesterWithCourses

class Repository(private val planDoa: PlansDao, semesterName: String) {
    val getClass: LiveData<List<SemesterWithCourses>> = planDoa.getSemesterWithCourse(semesterName)

    suspend fun insertSemester(newSemester: Semester) {
        planDoa.insertSemester(newSemester)
    }

    suspend fun insertCourse(newCourse: Course) {
        planDoa.insertCourse(newCourse)
    }

    suspend fun insertMeeting(newMeeting: Meeting) {
        planDoa.insertMeeting(newMeeting)
    }
}