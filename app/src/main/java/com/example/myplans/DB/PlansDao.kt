package com.example.myplans.DB

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.myplans.DB.Relations.SemesterWithCourses

@Dao
interface PlansDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertSemester(Semester: Semester): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertCourse(course: Course): Long


    @Transaction
    @Query("SELECT * FROM semester WHERE semester = :Semester")
    fun getSemesterWithCourse(Semester: String): LiveData<List<SemesterWithCourses>>

}