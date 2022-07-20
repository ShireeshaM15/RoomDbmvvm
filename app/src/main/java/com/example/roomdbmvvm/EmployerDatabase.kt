package com.example.roomdbmvvm

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Employer::class], version = 1)
abstract class EmployerDatabase : RoomDatabase() {
    abstract val EmployerDAO: EmployerDAO

    companion object {
        @Volatile
        private var INSTANCE: EmployerDatabase? = null
        fun getInstance(context: Context): EmployerDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        EmployerDatabase::class.java,
                        "employer_data_database"
                    ).build()
                }
                return instance
            }
        }
    }
}

