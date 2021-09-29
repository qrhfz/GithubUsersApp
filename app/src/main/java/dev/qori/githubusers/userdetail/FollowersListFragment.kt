package dev.qori.githubusers.userdetail

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.core.os.bundleOf
import dev.qori.githubusers.userlist.UserListFragment
import dev.qori.githubusers.userlist.UserListViewModel

class FollowersListFragment(): UserListFragment() {
    private lateinit var username: String

    override fun onAttach(context: Context) {
        super.onAttach(context)
        arguments?.getString(ARG_USERNAME)?.let {
            username = it
        }
    }
    override fun getUserListType(): UserListViewModel.UserListType = UserListViewModel.UserFollower(username)

    companion object{
        private const val ARG_USERNAME = "ARG_USERNAME"

        fun newInstance(username: String) = FollowersListFragment().apply {
            arguments = bundleOf(ARG_USERNAME to username)
        }
    }
}