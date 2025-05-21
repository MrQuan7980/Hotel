package com.example.appbookinghotel.viewmodel.room

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import  com.example.appbookinghotel.model.room.UserRoomRepository
import com.example.core.`object`.Room
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class StatusListRoom {
    data object Normal : StatusListRoom()
    data object Success : StatusListRoom()
    data class Error (val message : String) : StatusListRoom()

}
@HiltViewModel
class UserRoomViewModel @Inject constructor(
    private val userRoomRepository: UserRoomRepository
) : ViewModel() {
    private val _statusListRoom = MutableLiveData<StatusListRoom>()
    val status : LiveData<StatusListRoom> get() = _statusListRoom

    private val _rooms = MutableLiveData<List<Room>>()
    val room : LiveData<List<Room>> get() = _rooms

    fun showListRoom()
    {
        viewModelScope.launch {
            val result = userRoomRepository.getListRoom()
            result.fold(
                onSuccess = { rooms ->
                    _rooms.value = rooms
                    _statusListRoom.value = StatusListRoom.Success
                },
                onFailure = { error ->
                    _statusListRoom.value = StatusListRoom.Error(error.message.toString())
                }
            )
        }
    }
}