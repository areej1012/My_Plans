package com.example.myplans.DB

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.myplans.DB.Relations.SemesterWithCourses

@Dao
interface PlansDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertSemester(semester: Semester): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertCourse(course: Course): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertMeeting(meeting: Meeting): Long

    @Query("SELECT * From meeting where date >= :timeNow ORDER BY :timeNow")
    fun getMeeting(timeNow: String): LiveData<List<Meeting>>

    @Transaction
    @Query("SELECT * FROM semester WHERE semester = :Semester")
    fun getSemesterWithCourse(Semester: String): LiveData<List<SemesterWithCourses>>

}