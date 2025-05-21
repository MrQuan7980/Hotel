package com.example.core.api

import com.example.core.`object`.Booking
import com.example.core.`object`.Introduce
import com.example.core.`object`.Review
import com.example.core.`object`.Room
import com.example.core.`object`.User
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

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

    @GET("room/{key}.json")
    suspend fun getDetailRoom(@Path("key") key: String) : Room

    @DELETE("room/{key}.json")
    suspend fun deleteRoom(@Path("key") key: String) : Response<Void>

    @GET("review.json")
    suspend fun getReview() : Map<String, Review>

    @POST("review.json")
    suspend fun insertReview(@Body review: Review) : retrofit2.Response<Review>

    @DELETE("review/{key}.json")
    suspend fun deleteReview(@Path("key") key: String) : Response<Void>

    @GET("introduce.json")
    suspend fun getIntroduce() : Map<String, Introduce>

    // booking

    @POST("booking.json")
    suspend fun insertBooking(@Body booking: Booking) : retrofit2.Response<Booking>

}