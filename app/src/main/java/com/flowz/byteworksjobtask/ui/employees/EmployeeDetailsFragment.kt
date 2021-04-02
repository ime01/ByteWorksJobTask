package com.flowz.byteworksjobtask.ui.employees

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.flowz.byteworksjobtask.Model.Employee
import com.flowz.byteworksjobtask.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.employee_item_detail.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [EmployeeDetailsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */

@AndroidEntryPoint
class EmployeeDetailsFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var employee: Employee? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

            employee = EmployeeDetailsFragmentArgs.fromBundle(it).employee

            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.employee_item_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        first_name.setText( getString(R.string.de_first_name) + employee?.firstName)
        last_name.setText( getString(R.string.de_last_name) + employee?.lastName)
        male.setText( getString(R.string.de_gender) + employee?.gender)
        designation.setText( getString(R.string.de_designation) + employee?.desgination)
        date_of_birth.setText( getString(R.string.de_dob) + employee?.dateOfBirth)
        address.setText( getString(R.string.de_address) + employee?.address)
        country.setText( getString(R.string.de_country) + employee?.country)
        state.setText( getString(R.string.de_state) + employee?.state)

        Glide.with(reg_passport_photo)
            .load(employee?.passPortPhoto)
            .circleCrop()
            .placeholder(R.drawable.ic_baseline_person_24)
            .error(R.drawable.ic_baseline_person_24)
            .fallback(R.drawable.ic_baseline_person_24)
            .into(reg_passport_photo)



    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment EmployeeDetailsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            EmployeeDetailsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}