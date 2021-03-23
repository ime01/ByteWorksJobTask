package com.flowz.byteworksjobtask.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.flowz.byteworksjobtask.Model.Admin
import com.flowz.byteworksjobtask.roomdb.AdminDao
import com.flowz.byteworksjobtask.roomdb.CompanyDataBase
import com.flowz.byteworksjobtask.roomdb.DummyDataSoure
import com.flowz.byteworksjobtask.roomdb.EmployeeDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Provider
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providesCompanyDatabase(@ApplicationContext app: Context, employeeDao: Provider<EmployeeDao>, adminDao: Provider<AdminDao>) =
        Room.databaseBuilder(app, CompanyDataBase::class.java,  DATABASENAME).addCallback(object: RoomDatabase.Callback(){
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)

                val employeeList = DummyDataSoure().DummyListOfEmployees()
                val adminList = DummyDataSoure().DummyListOfAdmins()

                GlobalScope.launch (Dispatchers.IO){
                    employeeDao.get().insertList(employeeList)
                    adminDao.get().insertList(adminList)


                }

            }

        }).fallbackToDestructiveMigration()
            .build()

}

@Provides
@Singleton
fun providesEmployeesDao(db:CompanyDataBase) = db.EmployeeDao()

@Provides
@Singleton
fun providesAdminDao(db:CompanyDataBase) = db.AdminDao()


const val DATABASENAME = "companystaff_db"