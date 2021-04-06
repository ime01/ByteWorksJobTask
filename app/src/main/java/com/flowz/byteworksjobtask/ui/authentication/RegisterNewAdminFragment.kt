package com.flowz.byteworksjobtask.ui.authentication

import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.app.ActivityCompat
import androidx.core.net.toUri
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
import com.flowz.byteworksjobtask.Model.modelsfromjsonfile.CountryStateModelLite
import com.flowz.byteworksjobtask.R
import com.flowz.byteworksjobtask.ui.admin.AdminViewModel
import com.flowz.byteworksjobtask.util.*
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_add_new_employee.*
import kotlinx.android.synthetic.main.fragment_register_new_admin.*
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
    private var countryList  = ArrayList<String>()
    private lateinit var countryFromJson : CountryStateModelLite
    private var spinnerStates  = ArrayList<String>()
    private lateinit var countryToDb : String
    private lateinit var dateOFbirthToDb : String
    private lateinit var stateToDb : String
    private  var valuesFromJson : String? = null

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
        dataStore = requireContext().createDataStore(name = "LOGIN")

        countryList.add(0, "Select Country")
        spinnerStates.add(0, "Select State")

        valuesFromJson  = loadJson(requireContext())

        countryFromJson = Gson().fromJson(valuesFromJson, CountryStateModelLite::class.java)

        countryFromJson.forEach {
            countryList.add(it.name)
        }

        Log.e("JSON", "JSON FILES GOTTEN FROM ASSETS + $countryFromJson")

        val arrayAdapter =  ArrayAdapter(this.requireActivity().applicationContext, R.layout.sp_text_view, countryList )
        val statesAdapter =  ArrayAdapter(this.requireActivity().applicationContext, R.layout.sp_text_view, spinnerStates )
        rg_country_spinner.adapter = arrayAdapter
        rg_state_spinner.adapter = statesAdapter

        rg_date_of_birth.setOnClickListener {
            selectDateOfBirth()
        }



        rg_country_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

                countryToDb = parent?.getItemAtPosition(position).toString()

                countryFromJson.forEach {
                    if (countryToDb == it.name){

                        val allStates = it.states

                        allStates.forEach {
                            spinnerStates.add(it.name)
                        }


                    }
                }

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }

        rg_state_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

                stateToDb = parent?.getItemAtPosition(position).toString()

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }



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
            val layoutInflater = LayoutInflater.from(this.context)
            val alertView = layoutInflater.inflate(R.layout.camera_or_gallery_alert_dialog, null)

            val alertDialog = MaterialAlertDialogBuilder(requireContext())
            alertDialog.setView(alertView)
            alertDialog.setTitle(getString(R.string.choose_image))
            alertDialog.setCancelable(false)
            val dialog = alertDialog.create()

            val openCameraImage = alertView.findViewById<ImageView>(R.id.rg_open_camera)
            val openGalleryImage = alertView.findViewById<ImageView>(R.id.open_gallery)


            dialog.show()

            openCameraImage.setOnClickListener {
                checkPermssion()
                openCamera()
                dialog.dismiss()
            }

            openGalleryImage.setOnClickListener {
                checkPermssion()
                pickImageFromGallery()
                dialog.dismiss()
            }
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
            }else if(rg_date_of_birth.text.toString()== getString(R.string.enter_birth_date)){
                rg_date_of_birth.setError(getString(R.string.enter_valid_input))
                return@setOnClickListener
            }else if(TextUtils.isEmpty(rg_address.text.toString())){
                rg_address.setError(getString(R.string.enter_valid_input))
                return@setOnClickListener
            } else if(rg_country_spinner.selectedItem.toString()==getString(R.string.spinner_select_country)){
                showSnackbar(ne_address, getString(R.string.ensure_country_choosen))
                return@setOnClickListener
            }
            else if(rg_state_spinner.selectedItem.toString()==getString(R.string.spinner_select_state)){
                showSnackbar(ne_address, getString(R.string.ensure_state_choosen))
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
                    dateOFbirthToDb,
                    imageUri,
                    rg_address.takeWords(),
                    countryToDb,
                    stateToDb
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
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when(requestCode){
            READIMAGE->{
                if (grantResults[0]== PackageManager.PERMISSION_GRANTED){
                    pickImageFromGallery()
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
            reg_passport_photo.setImageURI(imageUri)
            showSnackbar(rg_address, "Profile passport selected for upload....")
        }
        else if (requestCode == IMAGECAPUTRECODE && resultCode == Activity.RESULT_OK){

            val rgPhoto = data!!.extras?.get("data") as Bitmap
            reg_passport_photo.setImageBitmap(rgPhoto)
            imageUri = getImageUri(requireContext(), rgPhoto)
        }
    }


    private fun pickImageFromGallery() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(intent, REQUESTCODE )
    }

    private fun openCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent, IMAGECAPUTRECODE )
    }


    fun selectDateOfBirth(){

        val layoutInflater = LayoutInflater.from(this.requireContext())
        val setTimeDialogView = layoutInflater.inflate(R.layout.date_picker_alert_dialog, null)
        val enteredDate = setTimeDialogView.findViewById<DatePicker>(R.id.date_p)


        val alertDialog = MaterialAlertDialogBuilder(requireContext())
        alertDialog.setView(setTimeDialogView)
        alertDialog.setTitle(getString(R.string.enter_birth_date))
        alertDialog.setCancelable(false)
        alertDialog.setPositiveButton(getString(R.string.submit_birth_date), null)
        alertDialog.setNegativeButton(getString(R.string.cancel), null)
        val dialog = alertDialog.create()

        dialog.setOnShowListener {
            dialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener {

                dateOFbirthToDb = " ${enteredDate.dayOfMonth} - ${enteredDate.month} - ${enteredDate.year} "
               rg_date_of_birth.setText(dateOFbirthToDb)

                dialog.dismiss()

            }
            dialog.getButton(DialogInterface.BUTTON_NEGATIVE).setOnClickListener {
                dialog.dismiss()
            }
        }
        dialog.show()
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
            val IMAGECAPUTRECODE = 400
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