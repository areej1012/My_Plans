package com.example.myplans.DB

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.myplans.DB.Relations.CourseWithClass
import com.example.myplans.DB.Relations.SemesterWithCourses

@Dao
interface PlansDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertSemester(semester: Semester): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertCourse(course: Course): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertMeeting(meeting: Meeting): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertClass(newClass: ClassStudent): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertHomeWork(newHomeWork: HomeWork): Long

    @Query("SELECT * From meeting WHERE day >= :day AND month >= :month")
    fun getMeeting(day: String, month: String): LiveData<List<Meeting>>

    @Transaction
    @Query("SELECT * FROM semester WHERE semester = :Semester")
    fun getSemesterWithCourse(Semester: String): LiveData<List<SemesterWithCourses>>

    @Transaction
    @Query("SELECT * From course WHERE nameCourse = :courseName")
    fun getCourseWithClass(courseName: String): LiveData<List<CourseWithClass>>

    @Transaction
    @Query("SELECT * From classes")
    fun getClassStudent(): LiveData<List<ClassStudent>>

    @Transaction
    @Query("SELECT * From home_work")
    fun getHomeWork(): LiveData<List<HomeWork>>

    @Delete
    fun deleteClass(classStudent: ClassStudent)

}