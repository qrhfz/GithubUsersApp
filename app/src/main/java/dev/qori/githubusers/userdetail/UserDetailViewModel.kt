package dev.qori.githubusers.userdetail

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dev.qori.githubusers.api.ApiConfig
import dev.qori.githubusers.favorites.Favorite
import dev.qori.githubusers.favorites.FavoriteRepository
import dev.qori.githubusers.models.UserResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserDetailViewModel(application: Application, private val username: String) :
    ViewModel() {
    private val favoriteRepository = FavoriteRepository(application)

    private var _isFavorite: MutableLiveData<Boolean> = MutableLiveData()
    var isFavorite: LiveData<Boolean> = _isFavorite

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _user = MutableLiveData<UserResponse>()
    val user: LiveData<UserResponse> = _user

    private fun getUser() {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getUser(username)
        client.enqueue(object : Callback<UserResponse> {
            override fun onResponse(
                call: Call<UserResponse>,
                response: Response<UserResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _user.value = response.body()
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    private fun getFavoriteData() {
        favoriteRepository.getFavorite(username).observeForever {
            _isFavorite.value = it != null
        }
    }


    fun toggleFavorite(username: String) {
        if (isFavorite.value != true) {
            favoriteRepository.insert(Favorite(username))
        } else {
            favoriteRepository.delete(Favorite(username))
        }
    }

    init {
        getUser()
        getFavoriteData()
    }

    @Suppress("UNCHECKED_CAST")
    class Factory(private val application: Application, private val username: String) :
        ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(UserDetailViewModel::class.java)) {
                return UserDetailViewModel(application, username) as T
            } else {
                throw IllegalAccessException("ViewModel class not found")
            }
        }
    }

    companion object {
        const val TAG = "UserDetailViewModel"
    }
}