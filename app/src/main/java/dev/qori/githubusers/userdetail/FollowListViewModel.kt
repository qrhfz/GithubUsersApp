package dev.qori.githubusers.userdetail

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dev.qori.githubusers.api.ApiConfig
import dev.qori.githubusers.models.UserResponse
import dev.qori.githubusers.userlist.UserListViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowListViewModel(
    private val username: String,
    private val listType: FollowListFragment.ListType
) : UserListViewModel() {
    override fun getUsers() {
        val client = when (listType) {
            FollowListFragment.ListType.FOLLOWERS -> ApiConfig.getApiService()
                .getFollowers(username)
            FollowListFragment.ListType.FOLLOWING -> ApiConfig.getApiService()
                .getFollowing(username)
        }

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
            }
        })
    }

    init {
        getUsers()
    }

    companion object {
        private const val TAG = "FollowListViewModel"
    }

    @Suppress("UNCHECKED_CAST")
    class Factory(private val username: String, private val listType: FollowListFragment.ListType) :
        ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(FollowListViewModel::class.java)) {
                return FollowListViewModel(username, listType) as T
            }
            throw IllegalAccessException("ViewModel class not found")
        }
    }

}

