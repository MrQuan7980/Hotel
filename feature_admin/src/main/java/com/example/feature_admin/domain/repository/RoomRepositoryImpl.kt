package com.example.feature_admin.domain.repository

import com.example.core.`object`.Room
import com.example.feature_admin.data.datasource.GetListRoom
import com.example.feature_admin.data.repository.RoomRepository
import javax.inject.Inject

class RoomRepositoryImpl @Inject constructor(
        private val getListRoom: GetListRoom
) : RoomRepository{
    override suspend fun getListRoom(): Map<String, Room> {
        return getListRoom.getListRoom()
    }
}