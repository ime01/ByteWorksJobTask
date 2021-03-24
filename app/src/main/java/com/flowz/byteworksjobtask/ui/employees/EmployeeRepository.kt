package com.flowz.byteworksjobtask.ui.employees

import com.flowz.byteworksjobtask.Model.Employee
import com.flowz.byteworksjobtask.roomdb.EmployeeDao
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class EmployeeRepository @Inject constructor(private val employeeDao: EmployeeDao) {

    val employeesFromdb = employeeDao.getAllEmployees()

    suspend fun insertEmployee(employee: Employee){
        employeeDao.insertEmployee(employee)
    }


}