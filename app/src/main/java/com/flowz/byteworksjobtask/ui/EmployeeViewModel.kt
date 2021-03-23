package com.flowz.byteworksjobtask.ui

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.flowz.byteworksjobtask.Model.Employee
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EmployeeViewModel @ViewModelInject constructor(private var employeeRepository: EmployeeRepository): ViewModel() {

    val employeesFromdb = employeeRepository.employeesFromdb

    fun insertEmployee(employee: Employee){
        viewModelScope.launch (Dispatchers.IO){
            employeeRepository.insertEmployee(employee)
            Log.d(TAG, "Employee Inserted into Database successfully")

        }
    }

    companion object{
        const val TAG = "EmployeeVM"
    }


}

