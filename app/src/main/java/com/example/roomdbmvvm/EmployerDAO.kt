package com.example.roomdbmvvm

import androidx.lifecycle.LiveData
import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface EmployerDAO {

    @Insert
    suspend fun insertEmployer(employer: Employer) : Long

    @Update
    suspend fun updateEmployer(employer: Employer) : Int

    @Delete
    suspend fun deleteEmployer(employer: Employer) : Int

    @Query("DELETE FROM employer_data_table")
    suspend fun deleteAll() : Int

    @Query("SELECT * FROM employer_data_table")
    fun getAllEmployes():Flow<List<Employer>>
}