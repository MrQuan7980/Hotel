package com.example.appbookinghotel.view.auth

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.appbookinghotel.R
import com.example.appbookinghotel.activity.MainActivity
import com.example.appbookinghotel.databinding.ActivityRegisterBinding
import com.example.appbookinghotel.viewmodel.auth.RegisterStatus
import com.example.appbookinghotel.viewmodel.auth.RegisterViewModel
import com.example.core.local.PerformDataStore
import com.example.core.`object`.User
import com.example.core.utils.checkIsEmpty
import com.example.core.utils.setMessage
import com.example.core.utils.showProgressbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.UUID

@AndroidEntryPoint
class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private val registerViewModel : RegisterViewModel by viewModels()
    private var email : String = ""
    private var phone : String = ""
    private var name : String = ""
    private var password : String = ""
    private var id : String = UUID.randomUUID().toString()
    private lateinit var user : User
    private val dataStore : PerformDataStore by lazy {
        PerformDataStore(applicationContext)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        eventHandling()
        observeRegisterState()
    }
    private fun eventHandling()
    {
        binding.iconBack.setOnClickListener {
            startActivity(Intent(this, SignInActivity::class.java))
        }
        binding.btnRegister.setOnClickListener {
            if (isValidateRegister())
            {
                showProgressbar(true, binding.btnRegister, binding.progressbarRegister)
                registerUser()
            }
        }
    }
    private fun registerUser()
    {
        user = User(id, name, email, phone, password)
        registerViewModel.registerUser(user)
    }
    private fun isValidateRegister() : Boolean
    {
        name = binding.inputName.text.toString()
        email = binding.inputEmail.text.toString()
        phone = binding.inputPhone.text.toString()
        password = binding.inputPassword.text.toString()

        return when {
            checkIsEmpty(email) -> {
                setMessage(getString(R.string.checkEmail))
                false
            }
            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                setMessage(getString(R.string.formatEmail))
                false
            }
            checkIsEmpty(name) -> {setMessage(
                getString(R.string.inputName))
                false
            }
            checkIsEmpty(phone) -> {
                setMessage(getString(R.string.inputPhone))
                false
            }
            phone.length != 10 -> {
                setMessage(getString(R.string.formatPhone))
                false
            }
            checkIsEmpty(password) || password.length < 6 -> {
                setMessage(getString(R.string.inputPhone))
                false
            }
            else -> true
        }
    }
    private fun observeRegisterState()
    {
        registerViewModel.status.observe(this) { state ->
            when (state) {
                is RegisterStatus.Success -> saveDataStore()
                is RegisterStatus.Error -> {
                    setMessage(state.message)
                    showProgressbar(false, binding.btnRegister, binding.progressbarRegister)
                }
                is RegisterStatus.Normal -> {}
            }
        }
    }
    private fun saveDataStore()
    {
        showProgressbar(false, binding.btnRegister, binding.progressbarRegister)
        setMessage(getString(R.string.register_success))
        lifecycleScope.launch {
            dataStore.putBoolean(PerformDataStore.KEY_IS_SIGN_IN, true)
            dataStore.putString(PerformDataStore.KEY_ID, id)
            dataStore.putString(PerformDataStore.KEY_EMAIL, email)
            dataStore.putString(PerformDataStore.KEY_NAME, name)
            dataStore.putString(PerformDataStore.KEY_PHONE, phone)

            val intent = Intent(this@RegisterActivity, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(intent)
        }
    }
}