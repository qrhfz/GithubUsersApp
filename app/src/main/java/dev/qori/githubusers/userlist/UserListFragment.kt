package dev.qori.githubusers.userlist

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import dev.qori.githubusers.databinding.FragmentUserListBinding
import dev.qori.githubusers.userdetail.UserDetailActivity


abstract class UserListFragment : Fragment() {
    //view binding pada fragment https://developer.android.com/topic/libraries/view-binding#fragments
    private var _binding: FragmentUserListBinding? = null
    private val binding get() = _binding!!
    private var viewModel: UserListViewModel? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUserListBinding.inflate(inflater, container, false)
        val view = binding.root

        viewModel = getViewModel()
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val rvUser = binding.rvUser
        val progressBar = binding.progressBar
        rvUser.layoutManager = LinearLayoutManager(activity)
        val adapter = UserListAdapter(listOf(), moveToUserDetail)
        rvUser.adapter = adapter

        viewModel?.users?.observe(viewLifecycleOwner) {
            adapter.setData(it)
        }

        viewModel?.isLoading?.observe(viewLifecycleOwner) {
            if (it) {
                progressBar.visibility = View.VISIBLE
            } else {
                progressBar.visibility = View.GONE
            }
        }

        viewModel?.errorMessage?.observe(viewLifecycleOwner) { err ->
            if (err.isNotEmpty()) {
                Toast.makeText(activity, err, Toast.LENGTH_LONG).show()
            }
        }
    }

    private val moveToUserDetail: OnItemClickCallback = { user ->
        val intent = Intent(activity, UserDetailActivity::class.java)
        intent.putExtra(UserDetailActivity.EXTRA_USER, user.username)
        startActivity(intent)
    }

    abstract fun getViewModel(): UserListViewModel

    override fun onDestroy() {
        _binding = null
        viewModel = null
        super.onDestroy()
    }
}