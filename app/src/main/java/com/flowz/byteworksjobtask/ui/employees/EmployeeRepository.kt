package com.flowz.byteworksjobtask.ui.employees

import com.flowz.byteworksjobtask.Model.apimodels.Countries
import com.flowz.byteworksjobtask.Model.Employee
import com.flowz.byteworksjobtask.roomdb.EmployeeDao
import com.flowz.printfuljobtask.network.ApiServiceCalls
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class EmployeeRepository @Inject constructor(private val apiClient: ApiServiceCalls, private val employeeDao: EmployeeDao) {


    val employeesFromdb = employeeDao.getAllEmployees()

    fun searchEmployee(searchQuery: String):Flow<List<Employee>>{
       return employeeDao.searchEmployee(searchQuery)
    }

    suspend fun insertEmployee(employee: Employee){
        employeeDao.insertEmployee(employee)
    }

    suspend fun fetchCountries(): Countries {
        return apiClient.FetchCountries()
    }


}