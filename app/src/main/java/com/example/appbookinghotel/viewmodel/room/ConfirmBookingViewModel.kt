package com.example.appbookinghotel.viewmodel.room

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appbookinghotel.model.room.ConfirmBookingRepository
import com.example.core.`object`.Booking
import com.example.core.`object`.Room
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class StatusConfirm {
    data object Normal : StatusConfirm()
    data object Success : StatusConfirm()
    data object BookingSuccess : StatusConfirm()
    data object UpdateSuccess : StatusConfirm()
    data class Error (val message : String) : StatusConfirm()

}
@HiltViewModel
class ConfirmBookingViewModel @Inject constructor(
    private val repository : ConfirmBookingRepository
) : ViewModel(){
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
    fun insertBooking(booking: Booking)
    {
        viewModelScope.launch {
            val result = repository.insertBooking(booking)
            result.fold(
                onSuccess = {
                    _statusConfirm.value = StatusConfirm.BookingSuccess
                },
                onFailure = { error ->
                    _statusConfirm.value = StatusConfirm.Error(error.message.toString())
                }
            )
        }
    }

    fun updateStatusRoom(number : Int)
    {
        viewModelScope.launch {
            val update = repository.updateStatusRoom(number)
            update.fold(
                onSuccess = {
                    _statusConfirm.value = StatusConfirm.UpdateSuccess
                },
                onFailure = { error ->
                    _statusConfirm.value = StatusConfirm.Error(error.message.toString())
                }
            )
        }
    }
}