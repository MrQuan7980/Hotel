package com.example.feature_admin.viewmodel.room

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.`object`.Room
import com.example.feature_admin.model.room.AddRoomRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class AddRoomState {
    object Normal : AddRoomState()
    object Success : AddRoomState()
    data class Error (val message : String) : AddRoomState()
}

@HiltViewModel
class AddRoomViewModel @Inject constructor(
    private val addRoomRepository: AddRoomRepository
) : ViewModel() {
    private val _addStatus = MutableLiveData<AddRoomState>(AddRoomState.Normal)
    val status : LiveData<AddRoomState> get() = _addStatus

    fun addRoom (room: Room)
    {
        viewModelScope.launch {
            val result = addRoomRepository.addRoom(room)
            result.onSuccess {
                _addStatus.value = AddRoomState.Success
            }.onFailure { error ->
                _addStatus.value = AddRoomState.Error(error.message.toString())
            }
        }
    }
}