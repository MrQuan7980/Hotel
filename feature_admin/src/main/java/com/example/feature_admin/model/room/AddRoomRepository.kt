package com.example.feature_admin.model.room

import com.example.core.api.RetrofitInstance
import com.example.core.`object`.Room
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

class AddRoomRepository {
    suspend fun addRoom (room: Room) : Result<Room>
    {
        return withContext (Dispatchers.IO) {
            try {
                val getRoom = RetrofitInstance.api.getRoom()
                val isNumberRoom = getRoom.values.any { it.numberRoom == room.numberRoom}

                if (isNumberRoom)
                {
                    return@withContext Result.failure(Exception("Số phòng đã tồn tại"))
                }
                val add : Response<Room> = RetrofitInstance.api.insertRoom(room)

                if (add.isSuccessful && add.body() != null)
                {
                    Result.success(add.body()!!)
                }
                else
                {
                    Result.failure(Exception("Add Room failed : ${add.code()}"))
                }
            }catch (e : Exception)
            {
                Result.failure(e)
            }
        }
    }
}