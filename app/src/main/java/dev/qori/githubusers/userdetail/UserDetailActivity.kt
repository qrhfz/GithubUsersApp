package dev.qori.githubusers.userdetail

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import dev.qori.githubusers.R
import dev.qori.githubusers.databinding.ActivityUserDetailBinding
import dev.qori.githubusers.models.UserResponse

class UserDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUserDetailBinding
    private var username: String? = null
    companion object {
        const val EXTRA_USERNAME = "extra_username"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        username = intent.getStringExtra(EXTRA_USERNAME)
        binding = ActivityUserDetailBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        if(username!=null){
            val viewModel: UserDetailViewModel by viewModels { UserDetailViewModelFactory(username as String) }

            viewModel.user.observe(this){userData->
                fillDetail(userData)
            }

            viewModel.isLoading.observe(this){isLoading->
                if(isLoading){
                    binding.clUserData.visibility = View.INVISIBLE
                    binding.progressBar2.visibility = View.VISIBLE

                }else{
                    binding.clUserData.visibility = View.VISIBLE
                    binding.progressBar2.visibility = View.GONE

                }
            }

            binding.btnShare.setOnClickListener(onShareButtonClickListener)

            binding.vpUserList.adapter = UserDetailPagerAdapter(this, username as String)
            TabLayoutMediator(binding.tabLayout, binding.vpUserList){tab, position->
                tab.text=when(position){
                    0-> "Followers"
                    else->"Following"
                }
            }.attach()


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
        binding.tvDetailLocation.text = user.location?:"N/A"
        binding.tvDetailCompany.text = user.company?:"N/A"
    }

    private val onShareButtonClickListener = View.OnClickListener {
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, "$username at https://github.com/$username")
            type = "text/plain"
        }

        val shareIntent = Intent.createChooser(sendIntent, "Share this github profile")
        startActivity(shareIntent)
    }



}