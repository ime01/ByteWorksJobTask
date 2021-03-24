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
            true,
            "President",
            "23/03/2021",
            R.drawable.p_trump,
            "White House",
        "USA",
            "Washington"

            )
        )

        employeeList.add(
            Employee(
                "Ronald",
                "Regan",
                true,
                "President",
                "23/03/2021",
                R.drawable.p_regan,
                "White House",
                "USA",
                "Washington"

            )
        )

        employeeList.add(
            Employee(
                "George",
                "Bush",
                true,
                "President",
                "23/03/2021",
                R.drawable.p_bush,
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
                true,
                "President",
                R.drawable.p_trump,
                "White House",
                "USA",
                "Washington"

            )
        )

        adminList.add(
            Admin(
                "Ronald",
                "Regan",
                true,
                "President",
                R.drawable.p_regan,
                "White House",
                "USA",
                "Washington"

            )
        )

        return adminList


    }
}