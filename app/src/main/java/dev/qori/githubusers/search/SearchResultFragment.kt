package dev.qori.githubusers.search

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import dev.qori.githubusers.R
import dev.qori.githubusers.userdetail.FollowListViewModel
import dev.qori.githubusers.userlist.UserListFragment
import dev.qori.githubusers.userlist.UserListViewModel

class SearchResultFragment : UserListFragment(){
    private lateinit var query: String
    override fun onAttach(context: Context) {
        super.onAttach(context)
        query = requireArguments().getString(ARG_QUERY)?:""

    }
    override fun getViewModel(): UserListViewModel {
        val viewModel: UserListViewModel by viewModels { SearchResultViewModel.Factory(query) }
        return viewModel
    }

    companion object{
        const val ARG_QUERY = "ARG_QUERY"
        private const val TAG = "SearchResultFragment"
    }

}