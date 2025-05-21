package com.example.appbookinghotel.viewmodel.room

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.`object`.Room
import kotlinx.coroutines.launch

sealed class StatusPrice{
    data object Normal : StatusPrice()
    data object Success : StatusPrice()
    data class Error (val error : String) : StatusPrice()
}

class PriceRoomViewModel : ViewModel(){
    private val _statusPrice = MutableLiveData<StatusPrice>()
    val status : LiveData<StatusPrice> get() = _statusPrice

    private val _statusRoom = MutableLiveData<Room>()
    val room : LiveData<Room> get() = _statusRoom

    fun checkDataRoom(room: Room)
    {
        viewModelScope.launch {
            _statusRoom.value = room
            _statusPrice.value = StatusPrice.Success
        }
    }
}