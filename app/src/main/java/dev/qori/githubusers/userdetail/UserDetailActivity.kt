package dev.qori.githubusers.userdetail

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayoutMediator
import dev.qori.githubusers.databinding.ActivityUserDetailBinding
import dev.qori.githubusers.models.UserResponse

class UserDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUserDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val username = intent.getStringExtra(EXTRA_USER)
        binding = ActivityUserDetailBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val viewModel: UserDetailViewModel by viewModels {
            UserDetailViewModel.Factory(
                application,
                username ?: ""
            )
        }

        viewModel.user.observe(this) { user ->
            fillDetail(user)
        }

        binding.btnShare.setOnClickListener {
            username?.let { it1 -> share(it1) }
        }

        binding.vpUserList.adapter = username?.let { UserDetailPagerAdapter(this, it) }

        TabLayoutMediator(binding.tabLayout, binding.vpUserList) { tab, position ->
            tab.text = when (position) {
                0 -> "Followers"
                else -> "Following"
            }
        }.attach()

        viewModel.isFavorite.observe(this) {
            if (it) {
                binding.floatingActionButton.imageTintList =
                    ColorStateList.valueOf(Color.rgb(255, 0, 0))
            } else {
                binding.floatingActionButton.imageTintList =
                    ColorStateList.valueOf(Color.rgb(75, 75, 75))
            }
        }

        binding.floatingActionButton.setOnClickListener {
            username?.let { it1 -> viewModel.toggleFavorite(it1) }
        }


    }

    private fun share(username: String) {
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(
                Intent.EXTRA_TEXT,
                "$username at https://github.com/$username "
            )
            type = "text/plain"
        }

        val shareIntent = Intent.createChooser(sendIntent, "Share this github profile")
        startActivity(shareIntent)
    }

    private fun fillDetail(user: UserResponse) {
        Glide.with(this)
            .load(user.avatarUrl) // URL Gambar
            .circleCrop() // Mengubah image menjadi lingkaran
            .into(binding.civDetailAvatar)
        binding.tvDetailName.text = if (user.name.isNotEmpty()) user.name else "-"
        binding.tvDetailUsername.text = user.username
        binding.tvDetailFollower.text = user.followers.toString()
        binding.tvDetailFollowing.text = user.following.toString()
        binding.tvDetailRepository.text = user.repos.toString()
        binding.tvDetailLocation.text = if (user.location.isNotEmpty()) user.location else "-"
        binding.tvDetailCompany.text = if (user.company.isNotEmpty()) user.company else "-"
    }


    companion object {
        const val EXTRA_USER = "extra_user"
    }


}