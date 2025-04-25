package com.example.appbookinghotel.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appbookinghotel.model.RegisterRepository
import com.example.core.`object`.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerRepository : RegisterRepository
) : ViewModel() {
    private val _registerStatus = MutableLiveData<RegisterStatus>(RegisterStatus.Normal)
    val status : LiveData<RegisterStatus> = _registerStatus

    fun registerUser(user: User)
    {
        viewModelScope.launch {
            val result = registerRepository.registerUser(user)
            result.onSuccess {
                _registerStatus.value = RegisterStatus.Success
            }.onFailure { error ->
                _registerStatus.value = RegisterStatus.Error(error.message.toString())
            }
        }
    }
}

sealed class RegisterStatus {
    object Normal : RegisterStatus()
    object Success : RegisterStatus()
    data class Error (val message: String) : RegisterStatus()
}