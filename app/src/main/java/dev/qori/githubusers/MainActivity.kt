package dev.qori.githubusers

import android.app.SearchManager
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import androidx.appcompat.widget.SearchView
import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentManager
import dev.qori.githubusers.allusers.AllUserFragment
import dev.qori.githubusers.search.SearchResultFragment


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val mFragmentManager = supportFragmentManager
        val mHomeFragment = AllUserFragment()
        val fragment = mFragmentManager.findFragmentByTag(AllUserFragment::class.java.simpleName)

        if(fragment !is AllUserFragment){
            mFragmentManager
                .beginTransaction()
                .add(R.id.frame_container, mHomeFragment, AllUserFragment::class.java.simpleName)
                .commit()
        }

        setContentView(R.layout.activity_main)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_options, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.search).actionView as SearchView
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.search_hint)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                showSearchFragment(query)
                return true
            }
            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })

        return true
    }

    fun showSearchFragment(query: String){
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.frame_container,SearchResultFragment::class.java, bundleOf(SearchResultFragment.ARG_QUERY to query),"FRAGMENT_SEARCH")
            addToBackStack("ALL_TO_SEARCH")
            commit()
        }
    }

    override fun onBackPressed() {
        if(supportFragmentManager.findFragmentByTag("FRAGMENT_SEARCH")!=null){
            Log.d("MainActivity", "Im on fragment search")
            supportFragmentManager.popBackStack("ALL_TO_SEARCH", FragmentManager.POP_BACK_STACK_INCLUSIVE)
            title = resources.getString(R.string.app_name)
        }else{
            super.onBackPressed()
        }
    }
}