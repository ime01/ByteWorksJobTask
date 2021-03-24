package com.flowz.byteworksjobtask.ui.admin

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.flowz.byteworksjobtask.Model.Admin
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AdminViewModel @ViewModelInject constructor(private var adminRepository: AdminRepository): ViewModel() {

    val adminsFromdb = adminRepository.adminsFromdb

    fun insertAdmin(admin: Admin){
        viewModelScope.launch (Dispatchers.IO){
           adminRepository.insertAmin(admin)
            Log.d(TAG, "Admin into Database successfully")

        }
    }

    companion object{
        const val TAG = "AdminVM"
    }


}