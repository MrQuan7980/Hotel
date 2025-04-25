package com.example.core.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.Toast

fun Context.setMessage (message : String)
{
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun Context.checkIsEmpty(text : String) : Boolean
{
    return text.isEmpty()
}

inline fun <reified T : Activity> Context.openActivity(message: String) {
    val intent = Intent(this, T::class.java)
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
    startActivity(intent)
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

inline fun <reified T : Activity> Context.openIntent() {
    val intent = Intent(this, T::class.java)
    startActivity(intent)
}

fun Context.collectOtpDigits(variable1 : String, variable2 : String, variable3 : String , variable4 : String, variable5 : String) : String
{
    return listOf(variable1, variable2, variable3, variable4, variable5).joinToString("")
}

fun Context.focusNextOTPField(starting : EditText, end : EditText)
{
    starting.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(
            s: CharSequence?,
            start: Int,
            count: Int,
            after: Int
        ) {

        }
        override fun onTextChanged(
            s: CharSequence?,
            start: Int,
            before: Int,
            count: Int
        ) {
            if (s?.length == 1)
            {
                end.requestFocus()
            }
        }
        override fun afterTextChanged(s: Editable?) {
        }

    })
}

fun Context.showView(view : View, status : Int)
{
    view.visibility = status
}

fun Context.showProgressbar (
    isLoading : Boolean,
    button : View,
    progressbar : View
    ) {
    button.visibility = if (isLoading) View.GONE else View.VISIBLE
    progressbar.visibility = if (isLoading) View.VISIBLE else View.GONE
}

fun Context.backOTPField(left : EditText, right : EditText)
{
    right.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(
            s: CharSequence?,
            start: Int,
            count: Int,
            after: Int
        ) {
        }
        override fun onTextChanged(
            s: CharSequence?,
            start: Int,
            before: Int,
            count: Int
        ) {
            if (s?.length == 0)
            {
                left.requestFocus()
            }
        }
        override fun afterTextChanged(s: Editable?) {
        }

    })
}




