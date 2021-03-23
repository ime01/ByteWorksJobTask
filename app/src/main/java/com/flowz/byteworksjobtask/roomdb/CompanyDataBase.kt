package com.flowz.byteworksjobtask.roomdb

import androidx.room.Database
import androidx.room.RoomDatabase
import com.flowz.byteworksjobtask.Model.Admin
import com.flowz.byteworksjobtask.Model.Employee

@Database(entities = [Employee::class, Admin::class], version = 1, exportSchema = false)
abstract class CompanyDataBase:RoomDatabase() {

    abstract fun EmployeeDao(): EmployeeDao
    abstract fun AdminDao(): AdminDao
}