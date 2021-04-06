package com.flowz.byteworksjobtask.ui.employees

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.flowz.byteworksjobtask.Model.apimodels.Countries
import com.flowz.byteworksjobtask.Model.Employee
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import java.lang.Exception

class EmployeeViewModel @ViewModelInject constructor(private var employeeRepository: EmployeeRepository): ViewModel() {

    val employeesFromdb = employeeRepository.employeesFromdb
    val countriesFromApi = MutableLiveData<Countries>()
//    val se = MutableLiveData<Employee>()
//
//    val searchQuery = MutableStateFlow("")
//
//    @ExperimentalCoroutinesApi
//    private val taskFlow = searchQuery.flatMapLatest {
//        searchEmployee(it)
//    }
//
//    val searchedEmployeeResult = taskFlow


    fun insertEmployee(employee: Employee){
        viewModelScope.launch (Dispatchers.IO){
            employeeRepository.insertEmployee(employee)
            Log.d(TAG, "Employee Inserted into Database successfully")

        }
    }

    fun searchEmployee(searchQuery: String): LiveData<List<Employee>> {
        return employeeRepository.searchEmployee(searchQuery).asLiveData()
            Log.d(TAG, "Searched Successfull")
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

