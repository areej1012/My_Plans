package com.example.myplans.DB.Relations

import androidx.room.Embedded
import androidx.room.Relation
import com.example.myplans.DB.ClassStudent
import com.example.myplans.DB.Course

data class CourseWithClass(
    @Embedded val course: Course,
    @Relation(
        parentColumn = "nameCourse",
        entityColumn = "fk_nameCourse"
    )
    val classStudent: List<ClassStudent>
)
