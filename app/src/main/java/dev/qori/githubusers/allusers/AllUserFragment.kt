package dev.qori.githubusers.allusers

import dev.qori.githubusers.userlist.UserListFragment
import dev.qori.githubusers.userlist.UserListViewModel

class AllUserFragment: UserListFragment() {
    //fragmen ini menampilkan hasil dari api.github.com/users jumlahnya sesuai defaultnya api github.
    //belum tahu caranya pagination supaya infinite scroll
    override fun getViewModel(): UserListViewModel = AllUserViewModel()
}