package com.example.roomdbmvvm

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "employer_data_table")
data class Employer(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "employer_id")
    var id: Int,

    @ColumnInfo(name = "employer_name")
    var name: String,

    @ColumnInfo(name = "employer_email")
    var email: String

)