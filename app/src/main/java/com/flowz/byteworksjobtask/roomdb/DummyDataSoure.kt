package com.flowz.byteworksjobtask.roomdb

import com.flowz.byteworksjobtask.Model.Admin
import com.flowz.byteworksjobtask.Model.Employee
import com.flowz.byteworksjobtask.R

class DummyDataSoure {

    fun DummyListOfEmployees(): List<Employee>{
        val employeeList: ArrayList<Employee> = ArrayList()

        employeeList.add(
            Employee(
                "Donald",
                "Trump",
            "male",
            "President",
            "23/03/2021",
            null,
            "White House",
        "USA",
            "Washington"

            )
        )

        employeeList.add(
            Employee(
                "Ronald",
                "Regan",
                "male",
                "President",
                "23/03/2021",
                null,
                "White House",
                "USA",
                "Washington"

            )
        )

        employeeList.add(
            Employee(
                "George",
                "Bush",
                "male",
                "President",
                "23/03/2021",
                null,
                "White House",
                "USA",
                "Washington"

            )
        )

        return employeeList
    }



    fun DummyListOfAdmins(): List<Admin> {
        val adminList: ArrayList<Admin> = ArrayList()

        adminList.add(
            Admin(
                "Donald",
                "Trump",
                "male",
                "President",
                null,
                "White House",
                "USA",
                "Washington"

            )
        )

        adminList.add(
            Admin(
                "Ronald",
                "Regan",
                "male",
                "President",
                null,
                "White House",
                "USA",
                "Washington"

            )
        )

        return adminList


    }
}