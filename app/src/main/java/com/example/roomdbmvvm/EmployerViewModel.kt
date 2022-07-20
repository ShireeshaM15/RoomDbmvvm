package com.example.roomdbmvvm

import android.util.Patterns
import androidx.lifecycle.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class EmployerViewModel(private val repository: EmployerRepository) : ViewModel() {
    private var isUpdateOrDelete = false
    private lateinit var employerToUpdateOrDelete: Employer
    val inputName = MutableLiveData<String>()
    val inputEmail = MutableLiveData<String>()
    val saveOrUpdateButtonText = MutableLiveData<String>()
    val clearAllOrDeleteButtonText = MutableLiveData<String>()

    private val statusMessage = MutableLiveData<Event<String>>()
    val message: LiveData<Event<String>>
        get() = statusMessage

    init {
        saveOrUpdateButtonText.value = "Save"
        clearAllOrDeleteButtonText.value = "Clear All"
    }

    fun initUpdateAndDelete(employer: Employer) {
        inputName.value = employer.name
        inputEmail.value = employer.email
        isUpdateOrDelete = true
        employerToUpdateOrDelete = employer
        saveOrUpdateButtonText.value = "Update"
        clearAllOrDeleteButtonText.value = "Delete"
    }

    fun saveOrUpdate() {
        if (inputName.value == null) {
            statusMessage.value = Event("Please enter employee's name")
        } else if (inputEmail.value == null) {
            statusMessage.value = Event("Please enter employee's email")
        } else if (!Patterns.EMAIL_ADDRESS.matcher(inputEmail.value!!).matches()) {
            statusMessage.value = Event("Please enter a correct email address")
        } else {
            if (isUpdateOrDelete) {
                employerToUpdateOrDelete.name = inputName.value!!
                employerToUpdateOrDelete.email = inputEmail.value!!
                updateEmployer(employerToUpdateOrDelete)
            } else {
                val name = inputName.value!!
                val email = inputEmail.value!!
                insertEmployer(Employer(0, name, email))
                inputName.value = ""
                inputEmail.value = ""
            }
        }
    }

    private fun insertEmployer(employer: Employer) = viewModelScope.launch {
        val newRowId = repository.insert(employer)
        if (newRowId > -1) {
            statusMessage.value = Event("employer Inserted Successfully $newRowId")
        } else {
            statusMessage.value = Event("Error Occurred")
        }
    }


    private fun updateEmployer(employer: Employer) = viewModelScope.launch {
        val noOfRows = repository.update(employer)
        if (noOfRows > 0) {
            inputName.value = ""
            inputEmail.value = ""
            isUpdateOrDelete = false
            saveOrUpdateButtonText.value = "Save"
            clearAllOrDeleteButtonText.value = "Clear All"
            statusMessage.value = Event("$noOfRows Row Updated Successfully")
        } else {
            statusMessage.value = Event("Error Occurred")
        }
    }

    fun getSavedEmployers() = liveData {
        repository.employers.collect {
            emit(it)
        }
    }

    fun clearAllOrDelete() {
        if (isUpdateOrDelete) {
            deleteEmployer(employerToUpdateOrDelete)
        } else {
            clearAll()
        }
    }

    private fun deleteEmployer(employer: Employer) = viewModelScope.launch {
        val noOfRowsDeleted = repository.delete(employer)
        if (noOfRowsDeleted > 0) {
            inputName.value = ""
            inputEmail.value = ""
            isUpdateOrDelete = false
            saveOrUpdateButtonText.value = "Save"
            clearAllOrDeleteButtonText.value = "Clear All"
            statusMessage.value = Event("$noOfRowsDeleted Row Deleted Successfully")
        } else {
            statusMessage.value = Event("Error Occurred")
        }
    }

    private fun clearAll() = viewModelScope.launch {
        val noOfRowsDeleted = repository.deleteAll()
        if (noOfRowsDeleted > 0) {
            statusMessage.value = Event("$noOfRowsDeleted employers Deleted Successfully")
        } else {
            statusMessage.value = Event("Error Occurred")
        }
    }
}