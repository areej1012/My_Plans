package com.example.myplans.DB

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "semester")
data class Semester(
    @PrimaryKey(autoGenerate = true) val pk: Int,
    val semester: String,
    val startDate: String,
    val endDate: String
) {
}
