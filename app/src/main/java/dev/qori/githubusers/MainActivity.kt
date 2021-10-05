package dev.qori.githubusers

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SearchView
import androidx.core.os.bundleOf
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import dev.qori.githubusers.allusers.AllUserFragment
import dev.qori.githubusers.favorites.FavoriteFragment
import dev.qori.githubusers.search.SearchResultFragment
import dev.qori.githubusers.settings.SettingPreferences
import dev.qori.githubusers.settings.SettingsActivity
import dev.qori.githubusers.settings.SettingsViewModel

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
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

        val pref = SettingPreferences.getInstance(dataStore)

        val viewModel = ViewModelProvider(this, SettingsViewModel.Factory(pref)).get(
            SettingsViewModel::class.java
        )

        viewModel.getThemeSettings().observe(this){
                AppCompatDelegate.setDefaultNightMode(
                    if(it) AppCompatDelegate.MODE_NIGHT_YES
                    else AppCompatDelegate.MODE_NIGHT_NO)
        }
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

        menu.findItem(R.id.settings).setOnMenuItemClickListener {
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
            return@setOnMenuItemClickListener true
        }

        menu.findItem(R.id.favorites).setOnMenuItemClickListener {
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.frame_container,FavoriteFragment::class.java, null,FRAGMENT_FAVORITE)
                addToBackStack(ALL_TO_FAVS)
                commit()
            }
            return@setOnMenuItemClickListener true
        }

        return true
    }

    fun showSearchFragment(query: String){
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.frame_container,SearchResultFragment::class.java, bundleOf(SearchResultFragment.ARG_QUERY to query),"FRAGMENT_SEARCH")
            addToBackStack(ALL_TO_SEARCH)
            commit()
        }
    }



    override fun onBackPressed() {
        when {
            supportFragmentManager.findFragmentByTag(FRAGMENT_SEARCH)!=null -> {
                Log.d("MainActivity", "Im on fragment search")
                //supaya kalau back bakal pop hasil pencarian dan kembali menampilkan semua user
                supportFragmentManager.popBackStack(ALL_TO_SEARCH, FragmentManager.POP_BACK_STACK_INCLUSIVE)
                title = resources.getString(R.string.app_name)
            }
            supportFragmentManager.findFragmentByTag(FRAGMENT_FAVORITE)!=null -> {
                supportFragmentManager.popBackStack(ALL_TO_FAVS, FragmentManager.POP_BACK_STACK_INCLUSIVE)
                title = resources.getString(R.string.app_name)
            }
            else -> {
                super.onBackPressed()
            }
        }
    }

    companion object{
        private const val ALL_TO_SEARCH = "ALL_TO_SEARCH"
        private const val ALL_TO_FAVS = "ALL_TO_FAVS"
        private const val FRAGMENT_SEARCH = "FRAGMENT_SEARCH"
        private const val FRAGMENT_FAVORITE = "FRAGMENT_FAVORITE"
    }
}