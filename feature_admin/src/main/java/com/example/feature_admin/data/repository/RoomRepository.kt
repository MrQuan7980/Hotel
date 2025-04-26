package com.example.feature_admin.data.repository

import com.example.core.`object`.Room

interface RoomRepository {
    suspend fun getListRoom(): Map<String, Room>
}