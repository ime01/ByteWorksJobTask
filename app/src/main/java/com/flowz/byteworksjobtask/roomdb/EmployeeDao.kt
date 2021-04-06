package com.flowz.byteworksjobtask.roomdb

import androidx.lifecycle.LiveData
import androidx.room.*
import com.flowz.byteworksjobtask.Model.Employee
import com.flowz.byteworksjobtask.Model.UriConverters
import kotlinx.coroutines.flow.Flow

@Dao
interface EmployeeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEmployee(employee: Employee)

    @TypeConverters(UriConverters::class)
    @Query("SELECT * FROM employee_table ")
    fun getAllEmployees(): LiveData<List<Employee>>

    @TypeConverters(UriConverters::class)
    @Query("SELECT * FROM employee_table  WHERE firstName LIKE  :searchQuery OR lastName LIKE :searchQuery")
    fun searchEmployee(searchQuery: String): Flow<List<Employee>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertList(employees: List<Employee>)

}