package com.flowz.byteworksjobtask.roomdb

import androidx.lifecycle.LiveData
import androidx.room.*
import com.flowz.byteworksjobtask.Model.Admin
import com.flowz.byteworksjobtask.Model.UriConverters

@Dao
interface AdminDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAdmin(admin: Admin)

    @TypeConverters(UriConverters::class)
    @Query("SELECT * FROM admin_table ")
    fun getAllAdmins(): LiveData<List<Admin>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertList(admins: List<Admin>)
}