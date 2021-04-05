package com.flowz.byteworksjobtask.ui.authentication

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.preferencesKey
import androidx.datastore.preferences.createDataStore
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.flowz.byteworksjobtask.R
import com.flowz.byteworksjobtask.util.playAnimation
import com.flowz.byteworksjobtask.util.showSnackbar
import com.flowz.byteworksjobtask.util.takeWords
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_admin_login.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.first

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AdminLoginFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class AdminLoginFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var dataStore: DataStore<Preferences>
    private lateinit var readFirstName: String
    private lateinit var readPasswordName: String


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
        return inflater.inflate(R.layout.fragment_admin_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dataStore = requireContext().createDataStore(name = "LOGIN")
        playAnimation(this.requireContext(), R.anim.bounce, person )
        val navController : NavController = Navigation.findNavController(view)

        lifecycleScope.launch {
            readFirstName = ReadLoginInfo(FIRSTNAME).toString()
            readPasswordName = ReadLoginInfo(PASSWORD).toString()
        }


       ad_admin_login.setOnClickListener {

            if (TextUtils.isEmpty(lg_first_name.text.toString())){
                lg_first_name.setError(getString(R.string.firstname_error))
                return@setOnClickListener
            }

            else if (TextUtils.isEmpty(lg_password.text.toString())){
                lg_password.setError(getString(R.string.lastname_error))
                return@setOnClickListener
            }else{

                if (lg_first_name.takeWords() == readFirstName &&  lg_password.takeWords() == readPasswordName){
                    navController.navigate(R.id.action_adminLoginFragment_to_employeeFragment)
                }else{
                    showSnackbar(lg_first_name, getString(R.string.correct_details))
                }

            }
        }

        no_account.setOnClickListener {
            navController.navigate(R.id.action_adminLoginFragment_to_registerNewAdminFragment)
        }
        forgotten_password.setOnClickListener {
            navController.navigate(R.id.action_adminLoginFragment_to_forgotPassworrdFragment)
        }
    }

    private suspend fun ReadLoginInfo(key:String): String?{
        val dataStorekey = preferencesKey<String>(key)
        val preferences = dataStore.data.first()
        return preferences[dataStorekey]
    }



    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment AdminLoginFragment.
         */
        // TODO: Rename and change types and number of parameters

        val FIRSTNAME = "FIRSTNAME"
        val PASSWORD = "PASSWORD"

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AdminLoginFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}