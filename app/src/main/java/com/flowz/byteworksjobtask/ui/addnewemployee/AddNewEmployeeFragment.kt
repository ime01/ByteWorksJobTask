package com.flowz.byteworksjobtask.ui.addnewemployee

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioGroup
import androidx.fragment.app.viewModels
import com.flowz.byteworksjobtask.Model.Employee
import com.flowz.byteworksjobtask.R
import com.flowz.byteworksjobtask.util.clearTexts
import com.flowz.byteworksjobtask.util.showSnackbar
import com.flowz.byteworksjobtask.util.showToast
import com.flowz.byteworksjobtask.util.takeWords
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_add_new_employee.*
import kotlinx.android.synthetic.main.fragment_register_new_admin.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AddNewEmployeeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */

@AndroidEntryPoint
class AddNewEmployeeFragment : Fragment() {

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var gender : String

    private val addNewEmployeeViewModel by viewModels<AddEmployeeViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_new_employee, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val rbG = view.findViewById<RadioGroup>(R.id.ne_gender) as RadioGroup

        rbG.setOnCheckedChangeListener { group, checkedId ->

            when(checkedId){
                R.id.ne_male->{
                    gender = ne_male.text.toString()
                }
                R.id.ne_female->{
                    gender = ne_female.text.toString()
                }
            }
        }

        save_new_employee.setOnClickListener {

            if (TextUtils.isEmpty(ne_first_name.text.toString())){
                ne_first_name.setError(getString(R.string.enter_valid_input))
                return@setOnClickListener
            } else if (TextUtils.isEmpty(ne_last_name.text.toString())){
                ne_last_name.setError(getString(R.string.enter_valid_input))
                return@setOnClickListener
            }  else if(!ne_male.isChecked && !ne_female.isChecked){
                showToast(getString(R.string.choose_gender), this.requireContext())
                return@setOnClickListener
            }else if(TextUtils.isEmpty(ne_designation.text.toString())){
                ne_designation.setError(getString(R.string.enter_valid_input))
                return@setOnClickListener
            }else if(TextUtils.isEmpty(ne_date_of_birth.text.toString())){
                ne_date_of_birth.setError(getString(R.string.enter_valid_input))
                return@setOnClickListener
            }else if(TextUtils.isEmpty(ne_address.text.toString())){
                ne_address.setError(getString(R.string.enter_valid_input))
                return@setOnClickListener
            }else if(TextUtils.isEmpty(ne_country.text.toString())){
                ne_country.setError(getString(R.string.enter_valid_input))
                return@setOnClickListener
            }else if(TextUtils.isEmpty(ne_state.text.toString())){
                ne_state.setError(getString(R.string.enter_valid_input))
                return@setOnClickListener
            }
            else{
                val newEmployee = Employee(
                    ne_first_name.takeWords(),
                    ne_last_name.takeWords(),
                    gender,
                    ne_designation.takeWords(),
                    ne_date_of_birth.takeWords(),
                    null,
                    ne_address.takeWords(),
                    ne_country.takeWords(),
                    ne_state.takeWords()
                )


                addNewEmployeeViewModel.insertEmployee(newEmployee)
                showSnackbar(ne_address, getString(R.string.new_account_success))

                val arrayOfViewsToClearAfterSavingEmployee = arrayOf(ne_first_name,ne_last_name,ne_designation,ne_date_of_birth, ne_address, ne_country, ne_state)
                clearTexts(arrayOfViewsToClearAfterSavingEmployee)
                ne_male.isChecked = false
                ne_female.isChecked = false
            }

        }

    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment AddNewEmployeeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AddNewEmployeeFragment()
                .apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}