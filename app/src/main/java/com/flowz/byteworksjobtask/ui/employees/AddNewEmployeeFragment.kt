package com.flowz.byteworksjobtask.ui.employees

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.flowz.byteworksjobtask.Model.Employee
import com.flowz.byteworksjobtask.Model.modelsfromjsonfile.CountryStateModelLite
import com.flowz.byteworksjobtask.R
import com.flowz.byteworksjobtask.ui.authentication.RegisterNewAdminFragment
import com.flowz.byteworksjobtask.util.*
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_add_new_employee.*
import kotlinx.coroutines.*
import kotlin.concurrent.thread

@AndroidEntryPoint
class AddNewEmployeeFragment : Fragment() {

    private lateinit var gender : String
    private var countryList  = ArrayList<String>()
    private lateinit var countryFromJson : CountryStateModelLite
    private var spinnerStates  = ArrayList<String>()
    private lateinit var countryToDb : String
    private lateinit var stateToDb : String
    private  var valuesFromJson : String? = null
    private var imageUri : Uri? = null

    private val employeeViewModel by viewModels<EmployeeViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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

        countryList.add(0, "Select Country")
        spinnerStates.add(0, "Select State")

           val values = loadJson(requireContext())

                valuesFromJson = values

        countryFromJson = Gson().fromJson(valuesFromJson, CountryStateModelLite::class.java)

        countryFromJson.forEach {
            countryList.add(it.name)
        }

        Log.e("JSON", "JSON FILES GOTTEN FROM ASSETS + $countryFromJson")

        val arrayAdapter =  ArrayAdapter(this.requireActivity().applicationContext, R.layout.sp_text_view, countryList )
        val statesAdapter =  ArrayAdapter(this.requireActivity().applicationContext, R.layout.sp_text_view, spinnerStates )
        ne_country.adapter = arrayAdapter
        ne_state.adapter = statesAdapter


        ne_country.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{

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


        ne_state.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

                stateToDb = parent?.getItemAtPosition(position).toString()

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }

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

        ne_select_passport.setOnClickListener {
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
            }
            else if(ne_country.selectedItem.toString()==getString(R.string.spinner_select_country)){
                showSnackbar(ne_address, getString(R.string.ensure_country_choosen))
                return@setOnClickListener
            }
            else if(ne_state.selectedItem.toString()==getString(R.string.spinner_select_state)){
                showSnackbar(ne_address, getString(R.string.ensure_state_choosen))
                return@setOnClickListener
            }else if(imageUri == null){
                ne_select_passport.setError(getString(R.string.choose_passport))
                return@setOnClickListener
            }
            else{
                val newEmployee = Employee(
                    ne_first_name.takeWords(),
                    ne_last_name.takeWords(),
                    gender,
                    ne_designation.takeWords(),
                    ne_date_of_birth.takeWords(),
                    imageUri,
                    ne_address.takeWords(),
                        countryToDb,
                    stateToDb
                )


                employeeViewModel.insertEmployee(newEmployee)
                showSnackbar(ne_address, getString(R.string.new_employee_success))

                val arrayOfViewsToClearAfterSavingEmployee = arrayOf(ne_first_name,ne_last_name,ne_designation,ne_date_of_birth, ne_address)
                clearTexts(arrayOfViewsToClearAfterSavingEmployee)
                ne_male.isChecked = false
                ne_female.isChecked = false
                ne_passport_photo.setImageResource(R.drawable.ic_baseline_person_24)
                ne_country.firstVisiblePosition
                ne_state.firstVisiblePosition
            }

        }

    }


    fun checkPermssion(){
        if(Build.VERSION.SDK_INT>=23){
            if (ActivityCompat.checkSelfPermission(this.requireActivity()
                    ,android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){

                requestPermissions(arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),
                    RegisterNewAdminFragment.READIMAGE
                )
                return
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when(requestCode){
            RegisterNewAdminFragment.READIMAGE ->{
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
            ne_passport_photo.setImageURI(imageUri)
            showSnackbar(ne_passport_photo, "Profile passport selected for upload....")
        }
        else if (requestCode == RegisterNewAdminFragment.IMAGECAPUTRECODE && resultCode == Activity.RESULT_OK){

            val rgPhoto = data!!.extras?.get("data") as Bitmap
            ne_passport_photo.setImageBitmap(rgPhoto)

            imageUri = getImageUri(requireContext(), rgPhoto)

        }
    }


    private fun pickImageFromGallery() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(intent,
            REQUESTCODE
        )
    }

    private fun openCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent, RegisterNewAdminFragment.IMAGECAPUTRECODE)
    }


    companion object {
        val READIMAGE = 255
        val REQUESTCODE = 100
        val IMAGECAPUTRECODE = 400

    }
}