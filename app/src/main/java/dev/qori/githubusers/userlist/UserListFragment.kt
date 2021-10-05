package dev.qori.githubusers.userlist

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import dev.qori.githubusers.databinding.FragmentUserListBinding
import dev.qori.githubusers.userdetail.UserDetailActivity


abstract class UserListFragment : Fragment() {
    private var _binding: FragmentUserListBinding? = null
    private val binding get() = _binding!!
    private var viewModel:UserListViewModel? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUserListBinding.inflate(inflater, container, false)
        val view = binding.root

        viewModel = getViewModel()

        val rvUser = binding.rvUser
        val progressBar = binding.progressBar
        rvUser.layoutManager = LinearLayoutManager(activity)
        viewModel?.users?.observe(viewLifecycleOwner){
             rvUser.adapter = UserListAdapter(it, moveToUserDetail)
         }

        viewModel?.isLoading?.observe(viewLifecycleOwner){
            if(it){
                progressBar.visibility = View.VISIBLE
            }else{
                progressBar.visibility = View.GONE
            }
        }
        return view
    }

    private val moveToUserDetail: OnItemClickCallback = { user->
        val intent = Intent(activity, UserDetailActivity::class.java)
        intent.putExtra(UserDetailActivity.EXTRA_USER, user)
        startActivity(intent)
    }

    abstract fun getViewModel():UserListViewModel

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}