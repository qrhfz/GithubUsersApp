package dev.qori.githubusers.userdetail

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class UserDetailPagerAdapter(activity: AppCompatActivity, private val username: String) :
    FragmentStateAdapter(activity) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> FollowListFragment.newInstance(username, FollowListFragment.ListType.FOLLOWERS)
            else -> FollowListFragment.newInstance(username, FollowListFragment.ListType.FOLLOWING)
        }
    }
}