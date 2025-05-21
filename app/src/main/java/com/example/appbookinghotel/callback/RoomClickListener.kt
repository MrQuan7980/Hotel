package com.example.appbookinghotel.callback

import com.example.core.`object`.Room

interface RoomClickListener {
    fun onRoomClick(room: Room)
}