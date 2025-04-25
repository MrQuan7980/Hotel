package com.example.feature_admin.model

import com.example.core.api.RetrofitInstance
import com.example.core.`object`.Room
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ListRoomRepository {
    suspend fun getRoom(): Result<List<Room>> {
        return withContext(Dispatchers.IO) {
            try {
                val roomMap = RetrofitInstance.api.getRoom()
                val roomList = roomMap.values.toList() // lấy ra list room
                if (roomList.isNotEmpty())
                {
                    return@withContext Result.success(roomList)
                }
                return@withContext Result.failure(Exception("Danh sách phòng bị rỗng"))
            } catch (e: Exception) {
                return@withContext Result.failure(e)
            }
        }
    }

}