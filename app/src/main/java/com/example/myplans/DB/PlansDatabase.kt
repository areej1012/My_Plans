package com.example.myplans.DB

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [
        Semester::class,
        Course::class,
        Task::class,
        HomeWork::class,
        Quiz::class,
        Meeting::class
    ], version = 1
)
abstract class PlansDatabase : RoomDatabase() {
    abstract val plansDao: PlansDao

    companion object {
        @Volatile
        private var INSTANCE: PlansDatabase? = null

        fun getDatabase(context: Context): PlansDatabase {
            val temInstance = INSTANCE
            if (temInstance != null) {
                return temInstance
            }
            // executed on single thread that will make one instance from DB
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    PlansDatabase::class.java,
                    "plans_db"
                ).fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}