package com.example.myplans.DB.Relations

import androidx.room.Embedded
import androidx.room.Relation
import com.example.myplans.DB.Course
import com.example.myplans.DB.Semester

data class SemesterWithCourses(
    @Embedded val semester: Semester,
    @Relation(
        parentColumn = "semester",
        entityColumn = "fk_semester"
    )
    val courses: List<Course>
)