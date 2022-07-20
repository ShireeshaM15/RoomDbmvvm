package com.example.roomdbmvvm

class EmployerRepository(private val dao: EmployerDAO) {

    val employers = dao.getAllEmployes()

    suspend fun insert(employer: Employer): Long {
        return dao.insertEmployer(employer)
    }

    suspend fun update(employer: Employer): Int {
        return dao.updateEmployer(employer)
    }

    suspend fun delete(employer: Employer): Int {
        return dao.deleteEmployer(employer)
    }

    suspend fun deleteAll(): Int {
        return dao.deleteAll()
    }
}

//getAllEmployes