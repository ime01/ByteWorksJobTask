package com.flowz.byteworksjobtask.ui.authentication

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.flowz.byteworksjobtask.R
import com.flowz.byteworksjobtask.util.playAnimation
import com.flowz.byteworksjobtask.util.showSnackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_forgot_passworrd.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ForgotPassworrdFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class ForgotPassworrdFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_forgot_passworrd, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        playAnimation(this.requireContext(), R.anim.bounce, imageView4)

        button_send_reset_email.setOnClickListener {
            if (TextUtils.isEmpty(user_entered_reset_email.text.toString())){
                user_entered_reset_email.setError("Please enter your email")
                return@setOnClickListener
            }else{
                showSnackbar(user_entered_reset_email, getString(R.string.reset_email_sent))
            }
        }
    }

}