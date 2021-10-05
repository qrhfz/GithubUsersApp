package dev.qori.githubusers.allusers

import android.util.Log
import dev.qori.githubusers.api.ApiConfig
import dev.qori.githubusers.models.UserResponse
import dev.qori.githubusers.userlist.UserListViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AllUserViewModel : UserListViewModel() {
    override fun getUsers() {
        val client = ApiConfig.getApiService().getUserList()

        mIsLoading.value = true
        client.enqueue(object : Callback<List<UserResponse>> {
            override fun onResponse(
                call: Call<List<UserResponse>>,
                response: Response<List<UserResponse>>
            ) {
                mIsLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        mUsers.value = responseBody
                    }
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<UserResponse>>, t: Throwable) {
                mIsLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
                mErrorMessage.value = t.message
            }
        })
    }

    companion object {
        private const val TAG = "AllUserViewModel"
    }

    init {
        getUsers()
    }
}