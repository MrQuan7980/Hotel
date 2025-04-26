package com.example.feature_admin.domain.usecase

import com.example.core.`object`.Room
import com.example.feature_admin.data.repository.RoomRepository
import javax.inject.Inject

class RoomUsecase @Inject constructor(
        private val roomRepository: RoomRepository
){
    suspend fun getListRoom() : Map<String, Room> {
        return roomRepository.getListRoom()
    }
}