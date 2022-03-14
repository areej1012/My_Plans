package com.example.myplans.DB

import androidx.lifecycle.LiveData

class Repository(private val planDoa: PlansDao) {
    // val getClass: LiveData<List<SemesterWithCourses>> = planDoa.getSemesterWithCourse(semesterName!!)

    suspend fun insertSemester(newSemester: Semester) {
        planDoa.insertSemester(newSemester)
    }

    suspend fun insertCourse(newCourse: Course) {
        planDoa.insertCourse(newCourse)
    }


    suspend fun insertMeeting(newMeeting: Meeting) {
        planDoa.insertMeeting(newMeeting)
    }

    fun getMeetings(timeNow: String): LiveData<List<Meeting>> {
        return planDoa.getMeeting(timeNow)
    }
}