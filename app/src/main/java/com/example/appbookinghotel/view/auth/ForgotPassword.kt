package com.example.appbookinghotel.view.auth

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.View
import com.example.appbookinghotel.R
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.appbookinghotel.databinding.ActivityForgotPasswordBinding
import com.example.appbookinghotel.viewmodel.auth.ErrorType
import com.example.appbookinghotel.viewmodel.auth.ForgotPasswordStatus
import com.example.appbookinghotel.viewmodel.auth.ForgotPasswordViewModel
import com.example.core.utils.backOTPField
import com.example.core.utils.checkIsEmpty
import com.example.core.utils.collectOtpDigits
import com.example.core.utils.focusNextOTPField
import com.example.core.utils.setMessage
import com.example.core.utils.showProgressbar
import com.example.core.utils.showView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ForgotPassword : AppCompatActivity() {
    private lateinit var binding : ActivityForgotPasswordBinding
    private val forgotPasswordViewModel : ForgotPasswordViewModel by viewModels()
    private var email : String = ""
    private var otp : String = ""
    private var password : String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForgotPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)
        eventHandling()
        observeViewModel()
        moveToNextOTPField()
    }
    private fun eventHandling()
    {
        binding.buttonForrgotPassword.setOnClickListener {
            if (isValidateEmail())
            {
                showProgressbar(true, binding.buttonForrgotPassword, binding.progressbarCheckEmail)
                forgotPasswordViewModel.verifyEmailAndReSendOtp(email)
            }
        }
        binding.buttonCheckOTP.setOnClickListener {
            if (isValidateOTP())
            {
                forgotPasswordViewModel.checkOTP(otp)
            }
        }
        binding.buttonChangePassword.setOnClickListener {
            if (isValidateNewPassword())
            {
                showProgressbar(true, binding.buttonChangePassword, binding.progressbarChangePassword)
                forgotPasswordViewModel.changePassword(password)
            }
        }
        binding.resendPassword.setOnClickListener {
            forgotPasswordViewModel.verifyEmailAndReSendOtp(email)
        }
    }
    private fun isValidateOTP() : Boolean
    {
        otp = collectOtpDigits(
            binding.edtOtpDigit1.text.toString(),
            binding.edtOtpDigit2.text.toString(),
            binding.edtOtpDigit3.text.toString(),
            binding.edtOtpDigit4.text.toString(),
            binding.edtOtpDigit5.text.toString()
        )
        return when {
            checkIsEmpty(otp) -> {
                setMessage(getString(R.string.errorInputOTP))
                false
            }
            else -> true
        }
    }
    private fun isValidateEmail() : Boolean
    {
        email = binding.inputEmail.text.toString()
        return when {
            checkIsEmpty(email) -> {
                setMessage(getString(R.string.checkEmail))
                false
            }
            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                setMessage(getString(R.string.formatEmail))
                false
            }
            else -> true
        }
    }
    private fun isValidateNewPassword() : Boolean
    {
        password = binding.inputNewPassword.text.toString()
        return when {
            checkIsEmpty(password) -> {
                setMessage(getString(R.string.checkPassword))
                false
            }
            else -> true
        }
    }
    private fun emailConfirmationStatus()
    {
        showProgressbar(false, binding.buttonForrgotPassword, binding.progressbarCheckEmail)
        showView(binding.viewCheckEmail, View.GONE)
        showView(binding.viewCheckOTP, View.VISIBLE)
    }
    private fun otpConfirmationStatus()
    {
        showView(binding.viewCheckOTP, View.GONE)
        showView(binding.viewChangePassword, View.VISIBLE)
    }
    private fun passwordConfirmationStatus()
    {
        showProgressbar(false, binding.buttonChangePassword, binding.progressbarChangePassword)
        val intent = Intent(this, SignInActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(intent)
    }
    private fun moveToNextOTPField()
    {
        focusNextOTPField(binding.edtOtpDigit1, binding.edtOtpDigit2)
        focusNextOTPField(binding.edtOtpDigit2, binding.edtOtpDigit3)
        focusNextOTPField(binding.edtOtpDigit3, binding.edtOtpDigit4)
        focusNextOTPField(binding.edtOtpDigit4, binding.edtOtpDigit5)

        backOTPField(binding.edtOtpDigit4, binding.edtOtpDigit5)
        backOTPField(binding.edtOtpDigit3, binding.edtOtpDigit4)
        backOTPField(binding.edtOtpDigit2, binding.edtOtpDigit3)
        backOTPField(binding.edtOtpDigit1, binding.edtOtpDigit2)
    }
    private fun observeViewModel()
    {
        forgotPasswordViewModel.forgotPasswordStatus.observe (this) { status ->
            when(status)
            {
                is ForgotPasswordStatus.Normal -> {}
                is ForgotPasswordStatus.EmailVerification -> emailConfirmationStatus()
                is ForgotPasswordStatus.OtpVerification -> otpConfirmationStatus()
                is ForgotPasswordStatus.ChangePassword -> passwordConfirmationStatus()
                is ForgotPasswordStatus.Error -> {
                    setMessage(status.message)
                    when(status.type) {
                        ErrorType.CHECK_EMAIL ->showProgressbar(false, binding.buttonChangePassword, binding.progressbarChangePassword)
                        ErrorType.CHECK_PASSWORD -> showProgressbar(false, binding.buttonForrgotPassword, binding.progressbarCheckEmail)
                        else -> {}
                    }
                }
            }
        }
    }
}

