package com.example.core.`object`

data class Booking(
    val adminId: String?,
    val bookingId : String?,
    val userId : String?,
    val title: String?,
    val numberRoom: Int?,
    val address: String?,
    val city: String?,
    val people : Int?,
    val image : String?,
    val money: Int?,
    val dateCheckIn: String?,
    val dateCheckOut: String?,
    val note: String?,
    val isCheckedIn : Boolean? = false,
    val isCheckedOut : Boolean? = false
)