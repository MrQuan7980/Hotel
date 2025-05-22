package com.example.appbookinghotel.viewmodel.history

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appbookinghotel.model.history.ActiveTripRepository
import com.example.core.`object`.Booking
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class StatusBooking {
    data object Normal : StatusBooking()
    data object Success : StatusBooking()
    data class Error (val error : String) : StatusBooking()
}

@HiltViewModel
class ActiveTripViewModel @Inject constructor(
    private val repository : ActiveTripRepository
) : ViewModel(){

    private val _statusBooking = MutableLiveData<StatusBooking>()
    val status : LiveData<StatusBooking> get() = _statusBooking

    private val _listBooking = MutableLiveData<List<Booking>>()
    val list : LiveData<List<Booking>> get() = _listBooking

    fun getListBooking()
    {
        viewModelScope.launch {
            val result = repository.getBooking()
            result.fold(
                onSuccess = { booking ->
                    _listBooking.value = booking
                    _statusBooking.value = StatusBooking.Success
                },
                onFailure = { error ->
                    _statusBooking.value = StatusBooking.Error(error.message.toString())
                }
            )
        }
    }
}