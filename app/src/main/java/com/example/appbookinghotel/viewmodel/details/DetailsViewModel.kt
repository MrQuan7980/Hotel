package com.example.appbookinghotel.viewmodel.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.`object`.Room
import kotlinx.coroutines.launch

sealed class StatusDetail{
    object Normal : StatusDetail()
    object Success : StatusDetail()
    data class Error(val error: String) : StatusDetail()
}
class DetailsViewModel : ViewModel() {
    private val _statusDetail  = MutableLiveData<StatusDetail>()
    val statusDetail : LiveData<StatusDetail> get() = _statusDetail

    private val _room = MutableLiveData<Room>()
    val room : LiveData<Room> get() = _room

    fun receivedDataRoom(room: Room)
    {
        viewModelScope.launch {
            _room.value = room
            _statusDetail.value = StatusDetail.Success
        }
    }
}