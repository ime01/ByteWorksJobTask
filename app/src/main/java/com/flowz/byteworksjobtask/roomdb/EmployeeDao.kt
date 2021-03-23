package com.flowz.byteworksjobtask.roomdb

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.flowz.byteworksjobtask.Model.Employee

@Dao
interface EmployeeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEmployee(employee: Employee)

    @Query("SELECT * FROM employee_table ")
    fun getAllEmployees(): LiveData<List<Employee>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertList(employees: List<Employee>)

//    @Query("SELECT * FROM employee_table WHERE lastName = :surName ")
//    fun searchForEmployee(surName: String): LiveData<List<Employee>>
}