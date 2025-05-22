package com.example.appbookinghotel.model.room

import com.example.core.api.RetrofitInstance
import com.example.core.`object`.Booking
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

class ConfirmBookingRepository {
    private lateinit var key : String
    suspend fun insertBooking(booking: Booking) : Result<Booking>
    {
        return withContext(Dispatchers.IO)
        {
            try {
                val confirm : Response<Booking> = RetrofitInstance.api.insertBooking(booking)

                if (confirm.isSuccessful && confirm.body() != null)
                {
                    return@withContext Result.success(confirm.body()!!)
                }
                return@withContext Result.failure(Exception("Thêm không thành công"))
            }
            catch (e : Exception)
            {
                return@withContext Result.failure(e)
            }
        }
    }

    suspend fun updateStatusRoom (number : Int) : Result<String>
    {
        return withContext(Dispatchers.IO)
        {
            try {
                val getRoom = RetrofitInstance.api.getRoom()
                val isCheckNumberRoom = getRoom.entries.find { it.value.numberRoom == number }

                if (isCheckNumberRoom == null)
                {
                    return@withContext Result.failure(Exception("Không tìm thấy phòng"))
                }

                key = isCheckNumberRoom.key

                val request = RetrofitInstance.api.updateStatus(key, mapOf("status" to  true))

                if (request.isSuccessful)
                {
                    return@withContext Result.success("Cập nhật thành công")
                }
                return@withContext Result.failure(Exception("Cập nhật thất bại"))

            }catch (e : Exception)
            {
                return@withContext Result.failure(e)
            }
        }
    }
}