package com.example.roomdbmvvm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

class EmployerViewModelFactory(
        private val repository: EmployerRepository
        ):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
     if(modelClass.isAssignableFrom(EmployerViewModel::class.java)){
         return EmployerViewModel(repository) as T
     }
        throw IllegalArgumentException("Unknown View Model class")
    }



}