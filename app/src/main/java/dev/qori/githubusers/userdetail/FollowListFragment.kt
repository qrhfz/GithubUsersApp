package dev.qori.githubusers.userdetail

import android.content.Context
import androidx.core.os.bundleOf
import dev.qori.githubusers.userlist.UserListFragment
import dev.qori.githubusers.userlist.UserListViewModel

class FollowListFragment(): UserListFragment() {
    private lateinit var username: String
    private lateinit var listType: ListType

    override fun onAttach(context: Context) {
        super.onAttach(context)
        arguments?.getString(ARG_USERNAME)?.let {
            username = it
        }
        arguments?.getSerializable(ARG_LIST_CONTENT)?.let {
            listType = it as ListType
        }
    }
    override fun getUserListType(): UserListViewModel.UserListType = when(listType){
        ListType.FOLLOWERS -> UserListViewModel.UserFollower(username)
        ListType.FOLLOWING -> UserListViewModel.UserFollowing(username)
    }

    companion object{
        private const val ARG_USERNAME = "ARG_USERNAME"
        private const val ARG_LIST_CONTENT= "ARG_LIST_CONTENT"

        @JvmStatic
        fun newInstance(username: String, listType: ListType) = FollowListFragment().apply {
            arguments = bundleOf(ARG_USERNAME to username, ARG_LIST_CONTENT to listType)
        }
    }

    enum class ListType{
        FOLLOWERS, FOLLOWING
    }
}
