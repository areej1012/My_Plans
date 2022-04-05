package com.example.myplans.DB

import androidx.lifecycle.LiveData

class Repository(private val planDoa: PlansDao) {
    // val getClass: LiveData<List<SemesterWithCourses>> = planDoa.getSemesterWithCourse(semesterName!!)

    fun insertSemester(newSemester: Semester) {
        planDoa.insertSemester(newSemester)
    }

    fun insertCourse(newCourse: Course) {
        planDoa.insertCourse(newCourse)
    }


    fun insertMeeting(newMeeting: Meeting) {
        planDoa.insertMeeting(newMeeting)
    }

    fun getMeetings(day: String, month: String): LiveData<List<Meeting>> {
        return planDoa.getMeeting(day, month)
    }

    fun getClassStudent(): LiveData<List<ClassStudent>> {
        return planDoa.getClassStudent()
    }

    fun getHomeWork(day: String, month: String): LiveData<List<HomeWork>> {
        return planDoa.getHomeWork()
    }
}