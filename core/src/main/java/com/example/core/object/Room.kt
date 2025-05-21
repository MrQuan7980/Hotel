package com.example.core.`object`

import java.io.Serializable

data class Room(
    val admin_id: String?,
    val title: String?,
    val numberRoom: Int?,
    val address: String?,
    val city: String?,
    val area: Int?,
    val maxGuests: Int?,
    val money: Int?,
    val disCount: Float?,
    val status: Boolean? = false,
    val date: String?,
    val imageOne: String?,
    val imageTwo: String?,
    val imageThree: String?,
    val imageFour: String?,
    val imageFive: String?,
    val isPaid: Boolean? = false
) : Serializable