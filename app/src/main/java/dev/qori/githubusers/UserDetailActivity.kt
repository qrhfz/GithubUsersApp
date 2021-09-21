package dev.qori.githubusers

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import dev.qori.githubusers.databinding.ActivityUserDetailBinding

class UserDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUserDetailBinding

    companion object {
        const val EXTRA_USER = "extra_user"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val user = intent.getParcelableExtra<User>(EXTRA_USER)
        binding = ActivityUserDetailBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.civDetailAvatar.setImageResource(user?.avatar?:0)
        binding.tvDetailName.text = user?.name
        binding.tvDetailUsername.text = user?.username
        binding.tvDetailFollower.text = user?.follower.toString()
        binding.tvDetailFollowing.text = user?.following.toString()
        binding.tvDetailRepository.text = user?.repository.toString()
        binding.tvDetailLocation.text = user?.location
        binding.tvDetailCompany.text = user?.company
    }


}