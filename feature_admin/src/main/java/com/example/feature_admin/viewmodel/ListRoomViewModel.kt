package com.example.feature_admin.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.`object`.Room
import com.example.feature_admin.model.ListRoomRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class ListRoomStatus {
    object Normal : ListRoomStatus()
    object Success : ListRoomStatus()
    data class Data (val data: List<Room>) : ListRoomStatus()
    data class Error (val message : String) : ListRoomStatus()
}
@HiltViewModel
class RoomListViewModel @Inject constructor(
    private val listRoomRepository: ListRoomRepository
) : ViewModel(){
    private val _statusListRoom = MutableLiveData<ListRoomStatus>(ListRoomStatus.Normal)
    val status : LiveData<ListRoomStatus> get() = _statusListRoom

    private val _rooms = MutableLiveData<List<Room>>()
    val room : LiveData<List<Room>> get() = _rooms

    fun checkListData()
    {
        if (_rooms.value.isNullOrEmpty())
        {
            if (_statusListRoom.value !is ListRoomStatus.Normal)
            {
                _statusListRoom.value = ListRoomStatus.Normal
            }
            showListRoom()
        }
        else {
            _statusListRoom.value = ListRoomStatus.Data(_rooms.value ?: emptyList())
        }
    }
    fun showListRoom()
    {
        viewModelScope.launch {
            val result = listRoomRepository.getRoom()
            result.fold(
                onSuccess = {rooms ->
                    if (_rooms.value != rooms)
                    {
                        _rooms.value = rooms
                        _statusListRoom.value = ListRoomStatus.Success
                    }
                    else
                    {
                        Log.d("RoomListViewModel", "Dữ liệu không đổi, không cập nhật lại UI")
                    }
                },
                onFailure = {error ->
                    _statusListRoom.value = ListRoomStatus.Error(error.message.toString())
                }
            )
        }
    }
}