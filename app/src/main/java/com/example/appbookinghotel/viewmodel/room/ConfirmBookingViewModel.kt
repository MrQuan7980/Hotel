package com.example.appbookinghotel.viewmodel.room

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.`object`.Room
import kotlinx.coroutines.launch

sealed class StatusConfirm {
    data object Normal : StatusConfirm()
    data object Success : StatusConfirm()
    data class Error (val message : String) : StatusConfirm()

}
class ConfirmBookingViewModel : ViewModel(){
    private val _statusConfirm = MutableLiveData<StatusConfirm>()
    val status : LiveData<StatusConfirm> get() = _statusConfirm

    private val _statusRoom = MutableLiveData<Room?>()
    val room : LiveData<Room?> get() = _statusRoom

    fun checkRoom (room: Room?)
    {
        viewModelScope.launch {
            _statusRoom.value = room
            _statusConfirm.value = StatusConfirm.Success
        }
    }
}