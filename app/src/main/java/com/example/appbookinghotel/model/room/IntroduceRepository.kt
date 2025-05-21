package com.example.appbookinghotel.model.room

import com.example.core.api.RetrofitInstance
import com.example.core.`object`.Introduce
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class IntroduceRepository {
    suspend fun getIntroduce() : Result<Introduce> {
        return withContext(Dispatchers.IO) {
            try {
                val introduce = RetrofitInstance.api.getIntroduce()
                val listIntroduce = introduce.values.firstOrNull()

                if (listIntroduce != null)
                {
                    return@withContext Result.success(listIntroduce)
                }
                return@withContext Result.failure(Exception("Gọi danh sach không thành công"))
            }catch (e : Exception)
            {
                return@withContext Result.failure(e)
            }
        }
    }
}