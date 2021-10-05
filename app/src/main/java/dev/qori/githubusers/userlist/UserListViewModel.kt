package dev.qori.githubusers.userlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dev.qori.githubusers.models.UserResponse


typealias MutableUserListLiveData = MutableLiveData<List<UserResponse>>
typealias UserListLiveData = LiveData<List<UserResponse>>

abstract class UserListViewModel : ViewModel() {
    protected val mUsers = MutableUserListLiveData()
    val users: UserListLiveData = mUsers

    protected val mIsLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = mIsLoading

    protected val mErrorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = mErrorMessage

    abstract fun getUsers()
}