package com.example.appbookinghotel.model.auth

import com.example.core.api.RetrofitInstance
import com.example.core.`object`.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SignInRepository {
    companion object {
        const val KEY_ADMIN = "admin"
        const val KEY_USER = "user"
    }
    suspend fun signIn (email : String, password : String) : Result<User>{
        return withContext(Dispatchers.IO) {
            try {
                val user = RetrofitInstance.api.getCallSignIn()
                val checkUser = user.values.find {
                    it.email == email && it.password == password
                }
                if (checkUser != null)
                {
                    when (checkUser.type)
                    {
                        KEY_ADMIN -> {
                            Result.success(checkUser)
                        }
                        KEY_USER -> {
                            Result.success(checkUser)
                        }
                        else -> {
                            Result.failure(Exception("Tài khoản không hợp lệ"))
                        }
                    }
                }
                else
                {
                    Result.failure(Exception("Email or password is incorrect"))
                }
            }catch (e : Exception)
            {
                Result.failure(e)
            }
        }
    }
}