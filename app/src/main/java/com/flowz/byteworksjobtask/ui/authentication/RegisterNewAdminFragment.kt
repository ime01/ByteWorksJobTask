package com.flowz.byteworksjobtask.ui.authentication

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioGroup
import androidx.datastore.preferences.core.preferencesKey
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.flowz.byteworksjobtask.Model.Admin
import com.flowz.byteworksjobtask.R
import com.flowz.byteworksjobtask.ui.admin.AdminViewModel
import com.flowz.byteworksjobtask.util.showSnackbar
import com.flowz.byteworksjobtask.util.showToast
import com.flowz.byteworksjobtask.util.takeWords
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_register_new_admin.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [RegisterNewAdminFragment.newInstance] factory method to
 * create an instance of this fragment.
 */

@AndroidEntryPoint
class RegisterNewAdminFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
//    private lateinit var dataStore : DataStore<Preferences>
    private lateinit var gender : String

    private val adminViewModel by viewModels<AdminViewModel>()

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
        return inflater.inflate(R.layout.fragment_register_new_admin, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navController : NavController = Navigation.findNavController(view)
//        dataStore = this.requireContext().createDataStore(name = "login")
        val rbG = view.findViewById<RadioGroup>(R.id.rg_gender) as RadioGroup

        rbG.setOnCheckedChangeListener { group, checkedId ->
            when(checkedId){
                R.id.rg_male->{
                    gender =  rg_male.text.toString()
                }
                R.id.rg_female->{
//
                    gender = rg_female.text.toString()
                }

            }
        }

        register_new_admin.setOnClickListener {

            if (TextUtils.isEmpty(rg_first_name.text.toString())){
                rg_first_name.setError(getString(R.string.enter_valid_input))
                return@setOnClickListener
            } else if (TextUtils.isEmpty(rg_last_name.text.toString())){
                rg_last_name.setError(getString(R.string.enter_valid_input))
                return@setOnClickListener
            } else if(!rg_male.isChecked && !rg_female.isChecked){
                showToast(getString(R.string.choose_gender), this.requireContext())
                return@setOnClickListener
            }else if(TextUtils.isEmpty(rg_date_of_birth.text.toString())){
                rg_date_of_birth.setError(getString(R.string.enter_valid_input))
                return@setOnClickListener
            }else if(TextUtils.isEmpty(rg_address.text.toString())){
                rg_address.setError(getString(R.string.enter_valid_input))
                return@setOnClickListener
            }else if(TextUtils.isEmpty(rg_country.text.toString())){
                rg_country.setError(getString(R.string.enter_valid_input))
                return@setOnClickListener
            }else if(TextUtils.isEmpty(rg_state.text.toString())){
                rg_state.setError(getString(R.string.enter_valid_input))
                return@setOnClickListener
            }
            else{

                val newAdmin = Admin(
                    rg_first_name.takeWords(),
                    rg_last_name.takeWords(),
                    gender,
                    rg_date_of_birth.takeWords(),
                    null,
                    rg_address.takeWords(),
                    rg_country.takeWords(),
                    rg_state.takeWords()
                )


                adminViewModel.insertAdmin(newAdmin)
                showSnackbar(rg_address, getString(R.string.new_account_success))

                navController.navigate(R.id.action_registerNewAdminFragment_to_adminLoginFragment)

//                val arrayOfViewsToClearAfterSavingEmployee = arrayOf(rg_first_name,rg_last_name,rg_date_of_birth, rg_address, rg_gender, rg_country, rg_state)

//                clearTexts(arrayOfViewsToClearAfterSavingEmployee)
            }

        }

        account_holder.setOnClickListener {
            navController.navigate(R.id.action_registerNewAdminFragment_to_adminLoginFragment)
        }
    }

//    private suspend fun saveLoginInfo(key1: String, firstName: String, key2: String, lastName: String){
//
//        val dataStoreFirstNameKey = preferencesKey<String>(key1)
//        val dataStoreLastNameKey = preferencesKey<String>(key2)
//        dataStore.edit {login->
//            login[dataStoreFirstNameKey] = firstName
//            login[dataStoreLastNameKey] = lastName
//        }



//    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment RegisterNewAdminFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            RegisterNewAdminFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}