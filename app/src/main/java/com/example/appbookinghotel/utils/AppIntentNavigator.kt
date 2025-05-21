package com.example.appbookinghotel.utils

import android.content.Context
import android.content.Intent
import com.example.appbookinghotel.view.auth.SignInActivity
import com.example.core.intent.IntentActivity

class AppIntentNavigator : IntentActivity {
    override fun screenTransition(context: Context) {
        val intent = Intent(context, SignInActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        context.startActivity(intent)
    }
}