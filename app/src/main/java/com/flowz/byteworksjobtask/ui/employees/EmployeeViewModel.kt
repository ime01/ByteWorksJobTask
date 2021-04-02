package com.flowz.byteworksjobtask.ui.employees

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.flowz.byteworksjobtask.Model.Countries
import com.flowz.byteworksjobtask.Model.Employee
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

class EmployeeViewModel @ViewModelInject constructor(private var employeeRepository: EmployeeRepository): ViewModel() {

    val employeesFromdb = employeeRepository.employeesFromdb
    val countriesFromApi = MutableLiveData<Countries>()

    fun insertEmployee(employee: Employee){
        viewModelScope.launch (Dispatchers.IO){
            employeeRepository.insertEmployee(employee)
            Log.d(TAG, "Employee Inserted into Database successfully")

        }
    }

     fun fetchCountries() {
         viewModelScope.launch {
             try {
                 countriesFromApi.postValue(employeeRepository.fetchCountries())
                 Log.e(TAG, "COUNTRIES GOTTEN SUCCESSFULLY")
             }catch (e:Exception){
                 e.printStackTrace()
                 Log.e(TAG, "COUNTRIES NOT GOTTEN")
             }
         }
    }

    companion object{
        const val TAG = "EmployeeVM"
    }


}

