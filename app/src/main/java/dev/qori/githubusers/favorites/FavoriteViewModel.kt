package dev.qori.githubusers.favorites

import android.app.Application
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dev.qori.githubusers.api.ApiConfig
import dev.qori.githubusers.models.UserResponse
import dev.qori.githubusers.userlist.UserListViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FavoriteViewModel(application: Application):UserListViewModel() {
    private val favoriteRepository = FavoriteRepository(application)
    override fun getUsers() {
        getFavoriteList().value?.map { favorite->
            val client = ApiConfig.getApiService().getUser(favorite.username)
            client.enqueue(object: Callback<UserResponse>{
                override fun onResponse(
                    call: Call<UserResponse>,
                    response: Response<UserResponse>
                ) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()
                        if (responseBody != null) {
                            val list = users.value as MutableList
                            list.add(responseBody)
                            mUsers.value = list
                        }
                    } else {
                        Log.e(TAG, "onFailure: ${response.message()}")
                    }
                }

                override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                    Log.e(TAG, "onFailure: ${t.message}")
                }
            })
        }
    }

    class Factory(private val application: Application): ViewModelProvider.NewInstanceFactory(){
        override fun <T : ViewModel?> create(modelClass: Class<T>): T = FavoriteViewModel(application) as T
    }

    private fun getFavoriteList() = favoriteRepository.getAllFavorites()

    companion object{
        private const val TAG = "FavoriteViewModel"
    }
}