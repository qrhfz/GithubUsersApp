package dev.qori.githubusers.favorites

import android.content.Context
import androidx.fragment.app.viewModels
import dev.qori.githubusers.userlist.UserListFragment
import dev.qori.githubusers.userlist.UserListViewModel

class FavoriteFragment : UserListFragment() {
    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity?.title = "Favorites"
    }

    override fun getViewModel(): UserListViewModel {
        val viewModel: FavoriteViewModel by viewModels { FavoriteViewModel.Factory(activity?.application) }
        return viewModel
    }

    companion object {
        private const val TAG = "FavoriteFragment"
    }
}