package com.flowz.introtooralanguage.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.ListAdapter
import com.bumptech.glide.Glide
import com.flowz.byteworksjobtask.Model.Employee
import com.flowz.byteworksjobtask.R
import kotlinx.android.synthetic.main.employee_item.view.*


class EmployeeAdapter  (val listener: RowClickListener)  :ListAdapter<Employee, EmployeeAdapter.OraNumViewHolder>(EmployeeDiffCallback()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):  OraNumViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.employee_item, parent, false)
        return OraNumViewHolder(view, listener)

    }

    override fun onBindViewHolder(holder: OraNumViewHolder, position: Int) {

        holder.bind(getItem(position))
        holder.itemView.setOnClickListener {
            listener.onItemClickListener(getItem(position))
        }

    }

    class OraNumViewHolder(view: View, val listener: RowClickListener): RecyclerView.ViewHolder(view){

        val play = itemView.findViewById<ImageView>(R.id.reg_passport_photo)

        fun bind(employee: Employee){

            val imageIcon = itemView.reg_passport_photo
            val imageUri = employee.passPortPhoto

            itemView.first_name.text = employee.firstName
            itemView.last_name.text = employee.lastName
//            itemView.male.text = employee.gender.toString()
//            itemView.designation.text = employee.desgination.toString()
//            itemView.date_of_birth.text = employee.dateOfBirth
//            itemView.address.text = employee.address
            itemView.country.text = employee.country

            Glide.with(imageIcon)
                .load(imageUri)
                .circleCrop()
                .placeholder(R.drawable.ic_baseline_person_24)
                .error(R.drawable.ic_baseline_person_24)
                .fallback(R.drawable.ic_baseline_person_24)
                .into(imageIcon)

            itemView.ei_parent.setOnClickListener {
//                    listener.onPlayOraWordClickListener(employee)
                listener.onItemClickListener(employee)
            }

        }

    }

    interface RowClickListener{
//        fun onPlayOraWordClickListener(employee: Employee)
        fun onItemClickListener(employee: Employee)

    }

}