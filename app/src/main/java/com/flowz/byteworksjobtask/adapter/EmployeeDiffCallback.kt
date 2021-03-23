package com.flowz.introtooralanguage.adapters

import androidx.recyclerview.widget.DiffUtil
import com.flowz.byteworksjobtask.Model.Employee


class EmployeeDiffCallback : DiffUtil.ItemCallback<Employee>(){
    override fun areItemsTheSame(oldItem: Employee, newItem: Employee): Boolean {
        return oldItem.employeeId== newItem.employeeId
    }
    override fun areContentsTheSame(oldItem: Employee, newItem: Employee): Boolean {
        return oldItem == newItem
    }
}