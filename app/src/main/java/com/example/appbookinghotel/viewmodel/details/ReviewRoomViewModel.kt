package com.example.appbookinghotel.viewmodel.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appbookinghotel.model.room.ReviewRoomRepository
import com.example.core.`object`.Review
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class StatusReview {
    object Normal : StatusReview()
    object Success : StatusReview()
    object PostSuccess : StatusReview()
    data class Update(val data : List<Review>) : StatusReview()
    data class Error(val error: String) : StatusReview()
}

@HiltViewModel
class ReviewRoomViewModel @Inject constructor(
    private val repository : ReviewRoomRepository
) : ViewModel() {
    private val _statusReview = MutableLiveData<StatusReview>()
    val status : LiveData<StatusReview> get() = _statusReview

    private val _listReview = MutableLiveData<List<Review>>()
    val review : LiveData<List<Review>> get() = _listReview

    fun getListReview(id : Int)
    {
        viewModelScope.launch {
            val result = repository.getListReview(id)
            result.fold(
                onSuccess = { list ->
                    _listReview.value = list
                    _statusReview.value = StatusReview.Success
                },
                onFailure = { error ->
                    _statusReview.value = StatusReview.Error(error.message.toString())
                }
            )
        }
    }

    fun postReview (review: Review)
    {
        viewModelScope.launch {
            val post = repository.getPostReview(review)
            post.fold(
                onSuccess = {
                    _statusReview.value = StatusReview.PostSuccess
                    getListReview(review.roomId!!)
                },
                onFailure = { error ->
                    _statusReview.value = StatusReview.Error(error.message.toString())
                }
            )
        }
    }
    fun deleteReview (reviewId : String)
    {
        viewModelScope.launch {
            val listReview = _listReview.value ?: emptyList()
            val deleteReview = repository.deleteReview(reviewId)

            deleteReview.fold(
                onSuccess = {
                    val update = listReview.filter { it.id != reviewId }
                    _listReview.value = update
                    _statusReview.value = StatusReview.Update(update)
                },
                onFailure = { error ->
                    _statusReview.value = StatusReview.Error(error.message.toString())
                }
            )
        }
    }
}