package dev.qori.githubusers.userdetail

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayoutMediator
import dev.qori.githubusers.databinding.ActivityUserDetailBinding
import dev.qori.githubusers.favorites.Favorite
import dev.qori.githubusers.favorites.FavoriteRepository
import dev.qori.githubusers.models.UserResponse

class UserDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUserDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val userData = intent.getParcelableExtra<UserResponse>(EXTRA_USER)
        binding = ActivityUserDetailBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val favoriteRepository = FavoriteRepository(application)
        userData?.let { user->
            fillDetail(user)

            binding.btnShare.setOnClickListener {
                val sendIntent: Intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, "${user.username} at https://github.com/${user.username} ")
                    type = "text/plain"
                }

                val shareIntent = Intent.createChooser(sendIntent, "Share this github profile")
                startActivity(shareIntent)
            }

            binding.vpUserList.adapter = UserDetailPagerAdapter(this, user.username as String)
            TabLayoutMediator(binding.tabLayout, binding.vpUserList) { tab, position ->
                tab.text = when (position) {
                    0 -> "Followers"
                    else -> "Following"
                }
            }.attach()

            binding.floatingActionButton.setOnClickListener {
                favoriteRepository.insert(Favorite(user.username))
            }
        }


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
        binding.tvDetailLocation.text = user.location ?: "N/A"
        binding.tvDetailCompany.text = user.company ?: "N/A"
    }


    companion object {
        const val EXTRA_USER = "extra_user"
    }


}