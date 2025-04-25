    package com.example.appbookinghotel.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appbookinghotel.model.SignInRepository
import com.example.appbookinghotel.model.SignInRepository.Companion.KEY_ADMIN
import com.example.core.`object`.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewModelSignIn @Inject constructor(
    private val signInRepository : SignInRepository
) : ViewModel(){

    private val _state = MutableLiveData<SignInState>(SignInState.Normal)
    val stateLiveData : LiveData<SignInState> = _state

    fun signViewModel (email : String, password : String)
    {
        viewModelScope.launch {
            val result = signInRepository.signIn(email, password)
            result.onSuccess {
                val user = result.getOrNull()
                if (user?.type == KEY_ADMIN)
                {
                    _state.value = SignInState.SignInAdmin(user)
                }
                else
                {
                    _state.value = SignInState.SignInUser(user)
                }
            }.onFailure { error ->
                _state.value = SignInState.Error(error.message.toString())
            }
        }
    }
}
sealed class SignInState {
    object Normal : SignInState()
    data class SignInUser (val user: User?) : SignInState()
    data class SignInAdmin (val user: User?) : SignInState()
    data class Error(val message : String) : SignInState()  
}
