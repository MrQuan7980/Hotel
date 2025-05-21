package com.example.appbookinghotel.view.auth

import android.os.Bundle
import android.util.Patterns
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.appbookinghotel.R
import com.example.appbookinghotel.activity.MainActivity
import com.example.appbookinghotel.databinding.ActivitySignInBinding
import com.example.appbookinghotel.viewmodel.auth.SignInState
import com.example.appbookinghotel.viewmodel.auth.ViewModelSignIn
import com.example.core.local.PerformDataStore
import com.example.core.`object`.User
import com.example.core.utils.checkIsEmpty
import com.example.core.utils.openActivity
import com.example.core.utils.openIntent
import com.example.core.utils.setMessage
import com.example.core.utils.showProgressbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SignInActivity : AppCompatActivity() {
    private lateinit var binding : ActivitySignInBinding
    private val viewmodel : ViewModelSignIn by viewModels()
    private var email : String = ""
    private var password : String = ""
    private val dataStore : PerformDataStore by lazy {
        PerformDataStore(applicationContext)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)
        eventHandling()
        observeSignInState()

        lifecycleScope.launchWhenStarted {
            val isSignedIn = dataStore.getBoolean(PerformDataStore.KEY_IS_SIGN_IN).first()
            if (isSignedIn) {
                openActivity<MainActivity>(getString(R.string.success))
            }
        }
    }
    private fun eventHandling()
    {
        binding.buttonRegister.setOnClickListener {
            openIntent<RegisterActivity>()
        }
        binding.forgotPassword.setOnClickListener {
            openIntent<ForgotPassword>()
        }
        binding.buttonSignIn.setOnClickListener {
            if (isValidate())
            {
                showProgressbar(true, binding.buttonSignIn, binding.progressbarSignIn)
                loginUser(email, password)
            }
        }
    }
    private fun loginUser(email : String, password : String)
    {
        viewmodel.signViewModel(email, password)
    }
    private fun isValidate() : Boolean
    {
        email = binding.inputEmail.text.toString().trim()
        password = binding.inputPassword.text.toString().trim()
        return when {
            checkIsEmpty(email) -> {setMessage(getString(R.string.checkEmail))
                false
            }
            !Patterns.EMAIL_ADDRESS.matcher(binding.inputEmail.text.toString()).matches() -> {setMessage(getString(R.string.formatEmail))
                false
            }
            checkIsEmpty(password) -> {setMessage(getString(R.string.checkPassword))
                false
            }
            else -> true
        }
    }
    private fun navigateToAdminHome()
    {
        openActivity<com.example.feature_admin.activity.AdminMainActivity>(getString(R.string.success_admin))
    }
    private fun observeSignInState()
    {
        viewmodel.stateLiveData.observe(this) { state ->
            when (state) {
                is SignInState.Normal -> {}
                is SignInState.SignInUser -> {
                    getDataStore(state.user)
                    navigateToUserHome()
                }
                is SignInState.SignInAdmin -> {
                    getDataStore(state.user)
                    navigateToAdminHome()
                }
                is SignInState.Error -> {
                    setMessage(state.message)
                    showProgressbar(false, binding.buttonSignIn, binding.progressbarSignIn)
                }
            }
        }
    }

    private fun navigateToUserHome()
    {
        openActivity<MainActivity>(getString(R.string.success))
    }
    private fun getDataStore(user: User?)
    {
        showProgressbar(false, binding.buttonSignIn, binding.progressbarSignIn)
        lifecycleScope.launch {
            dataStore.putBoolean(PerformDataStore.KEY_IS_SIGN_IN, true)
            dataStore.putString(PerformDataStore.KEY_ID, user?.id.toString())
            dataStore.putString(PerformDataStore.KEY_EMAIL, email)
            dataStore.putString(PerformDataStore.KEY_NAME, user?.name.toString())
            dataStore.putString(PerformDataStore.KEY_PHONE, user?.phone.toString())
        }
    }
}
