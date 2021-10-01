package dev.qori.githubusers.search

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dev.qori.githubusers.api.ApiConfig
import dev.qori.githubusers.models.SearchResponse
import dev.qori.githubusers.userlist.UserListViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchResultViewModel(private val query:String): UserListViewModel() {
    init {
        getUsers()
    }

    override fun getUsers() {
        val client = ApiConfig.getApiService().searchUser(query)

        mIsLoading.value = true
        client.enqueue(object : Callback<SearchResponse> {
            override fun onResponse(
                call: Call<SearchResponse>,
                response: Response<SearchResponse>
            ) {
                mIsLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        mUsers.value = responseBody.items
                    }
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
                mIsLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    companion object{
        private const val TAG = "FollowListViewModel"
    }

    @Suppress("UNCHECKED_CAST")
    class Factory(private val query:String): ViewModelProvider.NewInstanceFactory(){
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if(modelClass.isAssignableFrom(SearchResultViewModel::class.java)){
                return SearchResultViewModel(query) as T
            }
            throw IllegalAccessException("ViewModel class not found")
        }
    }

}