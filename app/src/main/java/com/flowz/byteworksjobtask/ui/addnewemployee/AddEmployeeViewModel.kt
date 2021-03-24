package com.flowz.byteworksjobtask.ui.addnewemployee

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.flowz.byteworksjobtask.Model.Employee
import com.flowz.byteworksjobtask.ui.employees.EmployeeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddEmployeeViewModel @ViewModelInject constructor(private var employeeRepository: EmployeeRepository): ViewModel() {


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

