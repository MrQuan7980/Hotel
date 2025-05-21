package com.example.feature_admin.model.room

import com.example.core.api.RetrofitInstance
import com.example.core.`object`.Room
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ListRoomRepository {
    private lateinit var key : String
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
            } catch (e: Exception)
            {
                return@withContext Result.failure(e)
            }
        }
    }
    suspend fun deleteRoomById(id : Int) : Result<String> {
        return withContext(Dispatchers.IO) {
            try {
                val getRoom = RetrofitInstance.api.getRoom()
                val isCheckNumberRoom = getRoom.entries.find { it.value.numberRoom == id }

                if (isCheckNumberRoom == null)
                {
                    return@withContext Result.failure(Exception("Số phòng không tồn tại"))
                }
                key = isCheckNumberRoom.key

                val response = RetrofitInstance.api.deleteRoom(key)

                if (response.isSuccessful)
                {
                    return@withContext Result.success("Xóa thành công")
                }
                return@withContext Result.failure(Exception("Xóa thất bại"))

            }catch (e: Exception)
            {
                return@withContext Result.failure(e)
            }
        }
    }
}