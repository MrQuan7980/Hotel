package com.example.appbookinghotel.viewmodel.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appbookinghotel.model.auth.ForgotPasswordRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class ForgotPasswordStatus {
    object Normal : ForgotPasswordStatus()
    object EmailVerification : ForgotPasswordStatus()
    object OtpVerification : ForgotPasswordStatus()
    object ChangePassword : ForgotPasswordStatus()
    data class Error(val type : ErrorType, val message : String) : ForgotPasswordStatus()
}
enum class ErrorType{
    CHECK_EMAIL,
    CHECK_OTP,
    CHECK_PASSWORD
}

@HiltViewModel
class ForgotPasswordViewModel @Inject constructor(
    private val repository : ForgotPasswordRepository
) : ViewModel(){
    private val _forgotPasswordStatus = MutableLiveData<ForgotPasswordStatus>(ForgotPasswordStatus.Normal)
    val forgotPasswordStatus : LiveData<ForgotPasswordStatus> get() = _forgotPasswordStatus

    fun verifyEmailAndReSendOtp (email : String)
    {
        viewModelScope.launch {
            val result = repository.verifyEmailAndSendOtp(email)

            result.onSuccess { key ->
                _forgotPasswordStatus.value = ForgotPasswordStatus.EmailVerification
            }.onFailure { error ->
                _forgotPasswordStatus.value =
                    ForgotPasswordStatus.Error(ErrorType.CHECK_EMAIL, error.message.toString())
            }
        }
    }
    fun checkOTP(otp : String)
    {
        viewModelScope.launch {
            val result = repository.checkOTP(otp)
            result.onSuccess {
                _forgotPasswordStatus.value = ForgotPasswordStatus.OtpVerification
            }.onFailure { error ->
                _forgotPasswordStatus.value =
                    ForgotPasswordStatus.Error(ErrorType.CHECK_OTP, error.message.toString())
            }
        }
    }
    fun changePassword (password : String)
    {
        viewModelScope.launch {
            val changePassword = repository.changePassword(password)
            changePassword.onSuccess {
                _forgotPasswordStatus.value = ForgotPasswordStatus.ChangePassword
            }.onFailure { error ->
                _forgotPasswordStatus.value =
                    ForgotPasswordStatus.Error(ErrorType.CHECK_PASSWORD, error.message.toString())
            }
        }
    }
}