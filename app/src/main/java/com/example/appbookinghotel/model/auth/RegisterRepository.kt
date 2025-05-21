package com.example.appbookinghotel.model.auth

import com.example.core.api.RetrofitInstance
import com.example.core.`object`.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

class RegisterRepository {
    suspend fun registerUser(user: User) : Result<User>
    {
        return withContext(Dispatchers.IO)
        {
            try {
                val getUser = RetrofitInstance.api.getUser()
                val isEmailExists = getUser.values.any { it.email == user.email }
                val isPhoneExists = getUser.values.any { it.phone == user.phone }

                if (isEmailExists)
                {
                    return@withContext Result.failure(Exception("Email đã tồn tại"))
                }
                if (isPhoneExists)
                {
                    return@withContext Result.failure(Exception("Số điện thoại đã tồn tại"))
                }
                val register : Response<User> = RetrofitInstance.api.insertUser(user)

                if (register.isSuccessful && register.body() != null)
                {
                    Result.success(register.body()!!)
                }
                else
                {
                    Result.failure(Exception("Register failed: ${register.code()}"))
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }
}