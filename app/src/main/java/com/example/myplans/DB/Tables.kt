package com.example.myplans.DB

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "semester")
data class Semester(
    @PrimaryKey(autoGenerate = true) val pk: Int,
    val semester: String,
    val startDate: String,
    val endDate: String
)

@Entity(tableName = "course")
data class Course(
    @PrimaryKey(autoGenerate = true) val pk: Int,
    val name: String,
    val everyWeek: Boolean,
    val days: String,
    val time: String,
    val fk_semester: Int
)

@Entity(tableName = "task")
data class Task(
    @PrimaryKey(autoGenerate = true) val pk: Int,
    val titleTask: String,
    val description: String,
    val isReminder: Boolean,
    val time: String,
    val fk: Int
)

@Entity(tableName = "home_work")
data class HomeWork(
    @PrimaryKey(autoGenerate = true) val pk: Int,
    val title: String,
    val description: String,
    val isReminder: Boolean,
    val fk: Int
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
    @PrimaryKey(autoGenerate = true) val pk: Int,
    val title: String,
    val date: String,
    val time: String,
    val Location: String
)