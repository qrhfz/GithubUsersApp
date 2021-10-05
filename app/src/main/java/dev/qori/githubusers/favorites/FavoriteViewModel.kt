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

class FavoriteViewModel(application: Application) : UserListViewModel() {
    private val favoriteRepository = FavoriteRepository(application)
    private val favoriteList = favoriteRepository.getAllFavorites()

    override fun getUsers() {
        favoriteList.observeForever { favList ->
            mUsers.value = mutableListOf()
            favList.forEach { fav ->
                fetchUser(fav.username) { userResponse ->
                    val users = mutableListOf<UserResponse>()
                    this.users.value?.forEach { user ->
                        users.add(user)
                    }
                    userResponse?.let { users.add(it) }
                    mUsers.value = users
                }
            }
        }
    }

    init {
        getUsers()
    }

    private fun fetchUser(username: String, responseCallback: (UserResponse?) -> Unit) {
        val client = ApiConfig.getApiService().getUser(username)
        client.enqueue(object : Callback<UserResponse> {
            override fun onResponse(
                call: Call<UserResponse>,
                response: Response<UserResponse>
            ) {
                if (response.isSuccessful) {
                    responseCallback(response.body())
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }


    @Suppress("UNCHECKED_CAST")
    class Factory(private val application: Application?) : ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(FavoriteViewModel::class.java)) {
                return application?.let { FavoriteViewModel(it) } as T
            } else {
                throw IllegalAccessException("ViewModel class not found")
            }
        }
    }

    companion object {
        private const val TAG = "FavoriteViewModel"
    }
}