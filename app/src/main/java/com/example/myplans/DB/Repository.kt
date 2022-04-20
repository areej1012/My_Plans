package com.example.myplans.DB

import androidx.lifecycle.LiveData
import com.example.myplans.DB.Relations.SemesterWithCourses

class Repository(private val planDoa: PlansDao) {
    // val getClass: LiveData<List<SemesterWithCourses>> = planDoa.getSemesterWithCourse(semesterName!!)

    fun insertSemester(newSemester: Semester): Long {
        return planDoa.insertSemester(newSemester)
    }

    fun insertCourse(newCourse: Course) {
        planDoa.insertCourse(newCourse)
    }

    fun insertClass(newClass: ClassStudent) {
        planDoa.insertClass(newClass)
    }

    fun insertMeeting(newMeeting: Meeting) {
        planDoa.insertMeeting(newMeeting)
    }

    fun insertHomeWork(newHomeWork: HomeWork): Long {
        return planDoa.insertHomeWork(newHomeWork)
    }

    fun getSemesterWithCourse(Semester: String): LiveData<List<SemesterWithCourses>> {
        return planDoa.getSemesterWithCourse(Semester)
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

    suspend fun deleteClass(classStudent: ClassStudent) {
        planDoa.deleteClass(classStudent)
    }
}