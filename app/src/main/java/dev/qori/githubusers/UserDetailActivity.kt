package dev.qori.githubusers

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import dev.qori.githubusers.databinding.ActivityUserDetailBinding

class UserDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUserDetailBinding
    private lateinit var user: User
    companion object {
        const val EXTRA_USER = "extra_user"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserDetailBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        user = intent.getParcelableExtra<User>(EXTRA_USER) as User
        fillDetail()
        binding.btnShare.setOnClickListener(onShareButtonClickListener)
    }

    private fun fillDetail() {
        binding.civDetailAvatar.setImageResource(user.avatar)
        binding.tvDetailName.text = user.name
        binding.tvDetailUsername.text = user.username
        binding.tvDetailFollower.text = user.follower.toString()
        binding.tvDetailFollowing.text = user.following.toString()
        binding.tvDetailRepository.text = user.repository.toString()
        binding.tvDetailLocation.text = user.location
        binding.tvDetailCompany.text = user.company
    }

    private val onShareButtonClickListener = View.OnClickListener {
        shareUserProfile()
    }

    private fun shareUserProfile(){
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, "${user.name} at https://github.com/${user.username}")
            type = "text/plain"
        }

        val shareIntent = Intent.createChooser(sendIntent, "Share this github profile")
        startActivity(shareIntent)
    }



}