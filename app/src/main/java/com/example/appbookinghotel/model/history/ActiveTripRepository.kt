package com.example.appbookinghotel.model.history

import com.example.core.api.RetrofitInstance
import com.example.core.`object`.Booking
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ActiveTripRepository {
    suspend fun getBooking() : Result<List<Booking>>
    {
        return withContext(Dispatchers.IO)
        {
            try {
                val booking = RetrofitInstance.api.getBooking()
                val list = booking.values.toList()
                if (list.isNotEmpty())
                {
                    return@withContext Result.success(list)
                }
                return@withContext Result.failure(Exception("Không lấy được dữ liệu "))
            }catch (e : Exception)
            {
                return@withContext Result.failure(e)
            }
        }
    }
}