package com.example.feature_admin.data.datasource

import com.example.core.api.RetrofitService
import com.example.core.`object`.Room
import javax.inject.Inject

class GetListRoomImpl @Inject constructor(
        private val retrofitService: RetrofitService
) : GetListRoom {
    override suspend fun getListRoom(): Map<String, Room> {
        return retrofitService.getRoom()
    }
}