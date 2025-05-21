package com.example.appbookinghotel.model.room

import com.example.core.api.RetrofitInstance
import com.example.core.`object`.Room
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext

class UserRoomRepository {
    suspend fun getListRoom():  Result<List<Room>> {
        return withContext(Dispatchers.IO) {
            try {
                val rooms = RetrofitInstance.api.getRoom()
                val roomList = rooms.values.toList()
                if (roomList.isNotEmpty()) {
                    return@withContext Result.success(roomList)
                }
                return@withContext  Result.failure(Exception("Không có phòng nào được trả về"))
            } catch (e: Exception)
            {
                return@withContext Result.failure(e)
            }
        }
    }
}
