package com.example.myplans.DB

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "semester")
data class Semester(
    @PrimaryKey(autoGenerate = false)
    val semester: String,
    val startDate: String,
    val endDate: String
)

@Entity(tableName = "course")
data class Course(
    @PrimaryKey(autoGenerate = false)
    val nameCourse: String,
    val description: String?,
    val fk_semester: String,
)

@Entity(tableName = "classes")
data class ClassStudent(
    @PrimaryKey(autoGenerate = true)
    val pk: Int?,
    val day: String,
    val timeStart: String,
    val timeEnd: String,
    val fk_nameCourse: String
)

@Entity(tableName = "task")
data class Task(
    @PrimaryKey(autoGenerate = true) val pk: Int?,
    val titleTask: String,
    val description: String,
    val isReminder: Boolean,
    val isCompleted: Boolean,
    val Date: String,
    val fk_nameCourse: String
)

@Entity(tableName = "home_work")
data class HomeWork(
    @PrimaryKey(autoGenerate = true) val pk: Int?,
    val title: String,
    val description: String?,
    val day: String,
    val month: String,
    val year: String,
    val dayReminder: String?,
    val monthReminder: String?,
    val yearReminder: String?,
    val fk_nameCourse: String
)

@Entity(tableName = "quiz")
data class Quiz(
    @PrimaryKey(autoGenerate = true) val pk: Int,
    val title: String,
    val date: String,
    val timeQuiz: String,
    val isReminder: Boolean,
    val beforeTime: Int,
    val location: String,
    val fk: Int
)

@Entity(tableName = "meeting")
data class Meeting(
    @PrimaryKey(autoGenerate = true) val pk: Int?,
    val title: String,
    val day: String,
    val month: String,
    val year: String,
    val time: String,
    val location: String?
)