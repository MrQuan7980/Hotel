package com.example.core.api

import com.example.core.`object`.Room
import com.example.core.`object`.User
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface RetrofitService {
    @GET("user.json")
    suspend fun getCallSignIn() : Map<String, User>

    @POST("user.json")
    suspend fun insertUser(@Body user: User) : retrofit2.Response<User>

    @GET("user.json")
    suspend fun getUser () : Map<String, User>

    @PATCH("user/{userId}.json")
    suspend fun forgotPassword(@Path("userId") key : String, @Body password : Map<String, String>) : Response<Void>

    @GET("room.json")
    suspend fun getRoom() : Map<String, Room>

    @POST("room.json")
    suspend fun insertRoom(@Body room: Room) : retrofit2.Response<Room>


}