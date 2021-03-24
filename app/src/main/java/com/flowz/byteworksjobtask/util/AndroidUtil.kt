package com.flowz.byteworksjobtask.util

import android.content.Context
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.net.Uri
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
//import com.localazy.android.Localazy.getString
import java.io.IOException


fun AppCompatActivity.showToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun TextInputEditText.takeWords() : String{
    return this.text.toString().trim()
}
fun EditText.takeWords() : String{
    return this.text.toString().trim()
}

fun clearTexts(views:  Array<EditText>) {
    views.forEach {
        it.text.clear()
    }
}

fun playAnimation(context: Context, int: Int, view: View) {

        val animation = AnimationUtils.loadAnimation(context, int)
        view.startAnimation(animation)
    }

fun showToast(string: String, context: Context) {

        Toast.makeText(context, string, Toast.LENGTH_LONG).show()
    }

fun showSnackbar(view: View, string: String) {

        Snackbar.make(view, string, Snackbar.LENGTH_LONG).show()
    }

