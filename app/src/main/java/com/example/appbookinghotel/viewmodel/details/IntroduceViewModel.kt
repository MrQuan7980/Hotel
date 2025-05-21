package com.example.appbookinghotel.viewmodel.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appbookinghotel.model.room.IntroduceRepository
import com.example.core.`object`.Introduce
import com.example.core.`object`.Review
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class StatusIntroduce {
    data object Normal : StatusIntroduce()
    data object Success : StatusIntroduce()
    data class Error(val error: String) : StatusIntroduce()
}
@HiltViewModel
class IntroduceViewModel @Inject constructor(
    private val repository : IntroduceRepository
) : ViewModel() {

    private val _statusIntroduce = MutableLiveData<StatusIntroduce>()
    val status : LiveData<StatusIntroduce> get() = _statusIntroduce

    private val _listIntroduce = MutableLiveData<Introduce>()
    val introduce : LiveData<Introduce> get() = _listIntroduce

    fun getIntroduce()
    {
        viewModelScope.launch {
            val response = repository.getIntroduce()
            response.fold(
                onSuccess = { data ->
                    _listIntroduce.value = data
                    _statusIntroduce.value = StatusIntroduce.Success
                },
                onFailure = { error ->
                    _statusIntroduce.value = StatusIntroduce.Error(error.message.toString())
                }
            )
        }
    }
}