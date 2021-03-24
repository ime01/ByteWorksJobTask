package com.flowz.byteworksjobtask.ui.admin

import com.flowz.byteworksjobtask.Model.Admin
import com.flowz.byteworksjobtask.Model.Employee
import com.flowz.byteworksjobtask.roomdb.AdminDao
import com.flowz.byteworksjobtask.roomdb.EmployeeDao
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AdminRepository @Inject constructor(private val adminDao: AdminDao) {

        val adminsFromdb = adminDao.getAllAdmins()

        suspend fun insertAmin(admin: Admin){
            adminDao.insertAdmin(admin)
        }


}