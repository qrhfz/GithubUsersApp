package dev.qori.githubusers.userdetail

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import dev.qori.githubusers.databinding.ActivityUserDetailBinding
import dev.qori.githubusers.models.UserResponse
import dev.qori.githubusers.userlist.UserListViewModel

class UserDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUserDetailBinding
    private lateinit var username: String
    companion object {
        const val EXTRA_USERNAME = "extra_username"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        username = intent.getStringExtra(EXTRA_USERNAME)!!
        binding = ActivityUserDetailBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val viewModel: UserDetailViewModel by viewModels { UserDetailViewModelFactory(username!!) }

        viewModel.user.observe(this){
            fillDetail(it)
        }

        binding.btnShare.setOnClickListener(onShareButtonClickListener)
    }

    private fun fillDetail(user: UserResponse) {
        Glide.with(this)
            .load(user.avatarUrl) // URL Gambar
            .circleCrop() // Mengubah image menjadi lingkaran
            .into(binding.civDetailAvatar)
        binding.tvDetailName.text = user.name
        binding.tvDetailUsername.text = user.username
        binding.tvDetailFollower.text = user.followers.toString()
        binding.tvDetailFollowing.text = user.following.toString()
        binding.tvDetailRepository.text = user.repos.toString()
        binding.tvDetailLocation.text = user.location
        binding.tvDetailCompany.text = user.company
    }

    private val onShareButtonClickListener = View.OnClickListener {
        shareUserProfile()
    }

    private fun shareUserProfile(){
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, "$username at https://github.com/$username")
            type = "text/plain"
        }

        val shareIntent = Intent.createChooser(sendIntent, "Share this github profile")
        startActivity(shareIntent)
    }



}