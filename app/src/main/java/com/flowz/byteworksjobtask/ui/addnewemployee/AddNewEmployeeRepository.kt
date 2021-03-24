package com.flowz.byteworksjobtask.ui.addnewemployee

import com.flowz.byteworksjobtask.Model.Employee
import com.flowz.byteworksjobtask.roomdb.EmployeeDao
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class AddNewEmployeeRepository @Inject constructor(private val employeeDao: EmployeeDao) {


    suspend fun insertEmployee(employee: Employee){
        employeeDao.insertEmployee(employee)
    }


}