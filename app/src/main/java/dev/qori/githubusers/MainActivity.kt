package dev.qori.githubusers

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val rvUser = findViewById<RecyclerView>(R.id.rvUser)
        rvUser.layoutManager = LinearLayoutManager(this)

        val users = LocalUserData(this).all
        val userAdapter= ListUserAdapter(this, users, moveToUserDetail)

        rvUser.adapter = userAdapter
    }

    private val moveToUserDetail: OnItemClickCallback = { user->
        val intent = Intent(this, UserDetailActivity::class.java)
        intent.putExtra(UserDetailActivity.EXTRA_USER, user)
        startActivity(intent)
    }
}