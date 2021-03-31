package com.flowz.byteworksjobtask.ui.authentication

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioGroup
import androidx.core.app.ActivityCompat
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.preferencesKey
import androidx.datastore.preferences.createDataStore
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.flowz.byteworksjobtask.Model.Admin
import com.flowz.byteworksjobtask.R
import com.flowz.byteworksjobtask.ui.admin.AdminViewModel
import com.flowz.byteworksjobtask.util.showSnackbar
import com.flowz.byteworksjobtask.util.showToast
import com.flowz.byteworksjobtask.util.takeWords
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_add_new_employee.*
import kotlinx.android.synthetic.main.fragment_register_new_admin.*
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

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

    private lateinit var gender : String
    private var imageUri : Uri? = null
    private lateinit var dataStore: DataStore<Preferences>

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
//        sharedPreferences = requireContext().getSharedPreferences("SAVED_PREFS", Context.MODE_PRIVATE)
        dataStore = requireContext().createDataStore(name = "LOGIN")


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

        rg_select_passport.setOnClickListener {
            checkPermssion()
        }


        register_new_admin.setOnClickListener {

            if (TextUtils.isEmpty(rg_first_name.text.toString())){
                rg_first_name.setError(getString(R.string.enter_valid_input))
                return@setOnClickListener
            } else if (TextUtils.isEmpty(rg_last_name.text.toString())){
                rg_last_name.setError(getString(R.string.enter_valid_input))
                return@setOnClickListener
            }else if (TextUtils.isEmpty(rg_password.text.toString())){
                rg_password.setError(getString(R.string.enter_valid_input))
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
            }else if(imageUri == null){
                rg_select_passport.setError(getString(R.string.choose_passport))
                return@setOnClickListener
            }
            else{
                val firstName = rg_first_name.takeWords()
                val password = rg_password.takeWords()

                lifecycleScope.launch{
                    saveLoginInfo(FIRSTNAME, firstName, PASSWORD, password)
                }


                val newAdmin = Admin(
                    rg_first_name.takeWords(),
                    rg_last_name.takeWords(),
                    gender,
                    rg_date_of_birth.takeWords(),
                    imageUri,
                    rg_address.takeWords(),
                    rg_country.takeWords(),
                    rg_state.takeWords()
                )


                adminViewModel.insertAdmin(newAdmin)
                showSnackbar(rg_address, getString(R.string.new_account_success))

                navController.navigate(R.id.action_registerNewAdminFragment_to_adminLoginFragment)

            }

        }

        account_holder.setOnClickListener {
            navController.navigate(R.id.action_registerNewAdminFragment_to_adminLoginFragment)
        }
    }

    private suspend fun saveLoginInfo(firstNamekey:String, firstNameValue:String, passwordkey:String, passWordValue:String){
        val firstNameDataStorekey = preferencesKey<String>(firstNamekey)
        val passwordDataStorekey = preferencesKey<String>(passwordkey)

        dataStore.edit {login->
            login[firstNameDataStorekey] = firstNameValue
            login[passwordDataStorekey] = passWordValue
        }

    }




//    }

    fun checkPermssion(){
        if(Build.VERSION.SDK_INT>=23){
            if (ActivityCompat.checkSelfPermission(this.requireActivity()
                    ,android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){

                requestPermissions(arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE), READIMAGE)
                return
            }
        }
        pickImage()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when(requestCode){
            READIMAGE->{
                if (grantResults[0]== PackageManager.PERMISSION_GRANTED){
                    pickImage()
                }else{
                    showToast("Cannnot access your images",this.requireContext() )
                }
            }else-> super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUESTCODE && resultCode == Activity.RESULT_OK && data!!.data != null ){

            imageUri = data.data
            passport_photo.setImageURI(imageUri)
            showSnackbar(rg_address, "Profile passport selected for upload....")

        }
    }


    private fun pickImage() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT

        startActivityForResult(intent, REQUESTCODE )
    }


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
            val READIMAGE = 253
            val REQUESTCODE = 101
            val FIRSTNAME = "FIRSTNAME"
            val PASSWORD = "PASSWORD"

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