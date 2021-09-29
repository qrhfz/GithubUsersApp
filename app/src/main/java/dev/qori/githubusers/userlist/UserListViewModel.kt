package dev.qori.githubusers.userlist

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dev.qori.githubusers.api.ApiConfig
import dev.qori.githubusers.models.UserResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

typealias MutableUserListLiveData = MutableLiveData<List<UserResponse>>
typealias UserListLiveData = LiveData<List<UserResponse>>

class UserListViewModel: ViewModel() {
    private val _users = MutableUserListLiveData()
    val users: UserListLiveData = _users



    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private fun getUsers(){
        _isLoading.value = true
        val client = ApiConfig.getApiService().getUserList()
        client.enqueue(object : Callback<List<UserResponse>>{
            override fun onResponse(
                call: Call<List<UserResponse>>,
                response: Response<List<UserResponse>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _users.value = responseBody
                    }
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<UserResponse>>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    init {
        getUsers()
    }

    companion object{
        const val TAG = "UserListViewModel"
    }
}