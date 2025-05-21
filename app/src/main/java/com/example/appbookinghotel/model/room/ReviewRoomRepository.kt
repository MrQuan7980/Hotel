package com.example.appbookinghotel.model.room

import android.util.Log
import com.example.core.api.RetrofitInstance
import com.example.core.`object`.Review
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

class ReviewRoomRepository {
    suspend fun getListReview(id : Int) : Result<List<Review>> {
        return withContext(Dispatchers.IO) {
            try {
                val review = RetrofitInstance.api.getReview()
                val listReview = review.values.toList().filter {
                    it.roomId != null && it.roomId == id
                }
                if (listReview.isNotEmpty())
                {
                    return@withContext Result.success(listReview)
                }
                return@withContext Result.failure(Exception("Không có đánh giá nào"))
            }catch (e : Exception)
            {
                return@withContext Result.failure(e)
            }
        }
    }

    suspend fun getPostReview(review: Review) : Result<Review>
    {
        return withContext(Dispatchers.IO) {
            try {
                val postReview : Response<Review> = RetrofitInstance.api.insertReview(review)

                if (postReview.isSuccessful && postReview.body() != null)
                {
                    return@withContext Result.success(postReview.body()!!)
                }
                return@withContext Result.failure(Exception("Error post review ${postReview.code()}"))
            }catch (e : Exception)
            {
                Result.failure(e)
            }
        }
    }

    suspend fun deleteReview (id : String) : Result<String> {
        return withContext(Dispatchers.IO) {
            try {
                val listReview = RetrofitInstance.api.getReview()
                val filterReview = listReview.entries.find { it.value.id == id}

                if (filterReview == null)
                {
                    return@withContext Result.failure(Exception("Mã đánh giá không tồn tại"))
                }
                val key = filterReview.key

                val callBack = RetrofitInstance.api.deleteReview(key)

                if (callBack.isSuccessful)
                {
                    return@withContext Result.success("Xóa thành công")
                }
                return@withContext Result.failure(Exception("Xóa thất bại"))
            }catch (e : Exception)
            {
                return@withContext Result.failure(e)
            }
        }
    }
}
