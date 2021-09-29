package dev.qori.githubusers.userlist

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dev.qori.githubusers.*


class UserListFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_user_list, container, false)

        val rvUser = root.findViewById<RecyclerView>(R.id.rvUser)
        rvUser.layoutManager = LinearLayoutManager(activity)

        val users = listOf<User>()
        val userAdapter= ListUserAdapter(users, moveToUserDetail)

        rvUser.adapter = userAdapter
        return root
    }

    private val moveToUserDetail: OnItemClickCallback = { user->
        val intent = Intent(activity, UserDetailActivity::class.java)
        intent.putExtra(UserDetailActivity.EXTRA_USER, user)
        startActivity(intent)
    }
}