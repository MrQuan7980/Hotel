package com.example.feature_admin.data.datasource

import com.example.core.`object`.Room

interface GetListRoom {
    suspend fun getListRoom(): Map<String, Room>
}