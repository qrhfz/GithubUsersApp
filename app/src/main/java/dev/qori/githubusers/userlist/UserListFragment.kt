package dev.qori.githubusers.userlist

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dev.qori.githubusers.*
import dev.qori.githubusers.models.UserResponse
import dev.qori.githubusers.userdetail.UserDetailActivity


abstract class UserListFragment : Fragment() {

    private var viewModel:UserListViewModel? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_user_list, container, false)

        viewModel = setViewModel()

        val rvUser = root.findViewById<RecyclerView>(R.id.rvUser)
        rvUser.layoutManager = LinearLayoutManager(activity)
        viewModel?.users?.observe(viewLifecycleOwner){
             rvUser.adapter = UserListAdapter(it, moveToUserDetail)
         }
        return root
    }

    private val moveToUserDetail: OnItemClickCallback = { user->
        val intent = Intent(activity, UserDetailActivity::class.java)
        intent.putExtra(UserDetailActivity.EXTRA_USERNAME, user.username)
        startActivity(intent)
    }

    abstract fun setViewModel():UserListViewModel

    override fun onDestroy() {
        viewModel = null
        super.onDestroy()
    }
}