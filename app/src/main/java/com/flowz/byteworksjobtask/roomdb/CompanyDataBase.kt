package com.flowz.byteworksjobtask.roomdb

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.flowz.byteworksjobtask.Model.Admin
import com.flowz.byteworksjobtask.Model.Employee
import com.flowz.byteworksjobtask.Model.UriConverters

@Database(entities = [Employee::class, Admin::class], version = 1, exportSchema = false)
@TypeConverters(UriConverters::class)
abstract class CompanyDataBase : RoomDatabase() {

    abstract fun EmployeeDao(): EmployeeDao
    abstract fun AdminDao(): AdminDao
}