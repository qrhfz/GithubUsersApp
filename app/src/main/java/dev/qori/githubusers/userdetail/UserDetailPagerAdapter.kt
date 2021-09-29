package dev.qori.githubusers.userdetail

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import dev.qori.githubusers.userlist.UserListFragment

class UserDetailPagerAdapter(activity: AppCompatActivity, private val username: String) : FragmentStateAdapter(activity){
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0-> FollowersListFragment.newInstance(username)
            else->FollowingListFragment.newInstance(username)
        }
    }
}