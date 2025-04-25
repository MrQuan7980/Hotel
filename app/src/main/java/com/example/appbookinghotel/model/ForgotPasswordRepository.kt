package com.example.appbookinghotel.model

import com.example.appbookinghotel.utils.SendEmail
import com.example.core.api.RetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ForgotPasswordRepository {
    private lateinit var userKey : String
    private lateinit var otp : String
    suspend fun verifyEmailAndSendOtp(email: String): Result<String>
    {
        return withContext (Dispatchers.IO) {
            try {
                otp = randomOTP()

                val getUser = RetrofitInstance.api.getUser()
                val isEmailExists = getUser.entries.find { it.value.email == email }

                if (isEmailExists == null) {
                    return@withContext Result.failure(Exception("Email không tồn tại"))
                }
                userKey = isEmailExists.key
                val SENDER_EMAIL = "appzingmp3@gmail.com"
                val SENDER_PASSWORD  = "kcyl ogdk csup lhoi"

                val gmail = SendEmail(SENDER_EMAIL, SENDER_PASSWORD)
                gmail.set_to(arrayOf(email))
                gmail.set_subject("🔐 Mã xác minh tài khoản Booking Hotel của bạn")

                val emailBody = """
                  🎵 Xin chào,  
                
                  Bạn vừa yêu cầu đặt lại mật khẩu cho tài khoản Zing MP3.  
                  Đây là mã xác minh của bạn:  
                
                  ${otp}  
                
                  Hãy nhập mã này vào ứng dụng để tiếp tục quá trình đặt lại mật khẩu.  
                  Nếu bạn không yêu cầu thao tác này, vui lòng bỏ qua email này.  
                
                  Chúc bạn có trải nghiệm tuyệt vời cùng Zing MP3! 🎶  
                
                  Trân trọng,  
                  Đội ngũ Zing MP3    
                  """
                    .trimIndent()

                gmail.setBody(emailBody)

                val isSuccess = gmail.send()

                if (!isSuccess)
                {
                    return@withContext Result.failure(Exception("Gửi OTP thất bại"))
                }
                return@withContext Result.success(otp)
            } catch (e: Exception) {
                return@withContext Result.failure(e)
            }
        }
    }
    suspend fun checkOTP(inputOTP : String) : Result<String>
    {
        return withContext (Dispatchers.IO) {
            if (inputOTP == otp)
            {
                return@withContext Result.success(otp)
            }
            return@withContext Result.failure(Exception("Bạn nhập OTP sai"))
        }
    }
    suspend fun changePassword(password : String) : Result<String>
    {
        return withContext (Dispatchers.IO) {
            try {
                val map = mapOf(
                    "password" to password
                )
                val response = RetrofitInstance.api.forgotPassword(userKey, map)

                if (response.isSuccessful) {
                    return@withContext Result.success(userKey)
                } else {
                    return@withContext Result.failure(Exception("Thay đổi mật khẩu thất bại"))
                }
            } catch (e: Exception) {
                return@withContext Result.failure(e)
            }
        }
    }
    private fun randomOTP() : String{
        return (10000..99999).random().toString()
    }
}