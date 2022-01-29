package com.example.myplans.DB

import androidx.room.*
import com.example.myplans.DB.Relations.SemesterWithCourses

@Dao
interface PlansDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertSemester(Semester: Semester): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCourse(course: Course): Long


    @Transaction
    @Query("SELECT * FROM semester WHERE semester = :Semester")
    suspend fun getSemesterWithCourse(Semester: String): List<SemesterWithCourses>

}