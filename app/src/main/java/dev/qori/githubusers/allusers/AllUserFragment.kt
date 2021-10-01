package dev.qori.githubusers.allusers

import dev.qori.githubusers.userlist.UserListFragment
import dev.qori.githubusers.userlist.UserListViewModel

class AllUserFragment: UserListFragment() {
    override fun getViewModel(): UserListViewModel = AllUserViewModel()
}