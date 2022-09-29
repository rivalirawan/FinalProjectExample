package com.mobileprogramming.myfundamentalapp.actfrag

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.mobileprogramming.myfundamentalapp.R
import com.mobileprogramming.myfundamentalapp.adapter.ViewPagerAdapter
import com.mobileprogramming.myfundamentalapp.databinding.ActivityUserDetailBinding
import com.mobileprogramming.myfundamentalapp.viewmodel.DetailViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UserDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUserDetailBinding
    private lateinit var detailViewModel: DetailViewModel
    lateinit var context: Context

    companion object {
        const val EXTRA_USERNAME = "extra_username"
        const val EXTRA_ID = "extra_id"
        const val EXTRA_AVATAR = "extra_avatar"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        context = this@UserDetailActivity

        supportActionBar?.title = getString(R.string.detail_app_name)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val username = intent.getStringExtra(EXTRA_USERNAME)
        val id = intent.getIntExtra(EXTRA_ID, 0)
        val avatarURL: String = intent.getStringExtra(EXTRA_AVATAR).toString()
        val bundle = Bundle()
        bundle.putString(EXTRA_USERNAME, username)

        detailViewModel = ViewModelProvider(this).get(DetailViewModel::class.java)

        username.let {
            if (it != null) {
                detailViewModel.setUser(it)
            }
        }

        detailViewModel.getUser().observe(this) {
            if (it != null) {
                binding.apply {
                    Glide.with(root)
                        .load(it.avatar_url)
                        .apply(RequestOptions())
                        .into(imgItemAvatar)
                    tvItemUsername.text = it.login
                    tvItemName.text = it.name
                    tvItemFollowers.text = it.followers.toString()
                    tvItemFollowing.text = it.following.toString()
                    tvItemRepository.text = it.public_repos.toString()
                    tvItemCompany.text = it.company
                    tvItemLocation.text = it.location
                }
            }
        }

        var isChecked = false
        lifecycleScope.launch(Dispatchers.IO) {
            val check = detailViewModel.checkUser(context, id)
            withContext(Dispatchers.Main) {
                if (check != null) {
                    isChecked = if (check > 0) {
                        binding.favoriteButton.setImageResource(R.drawable.ic_favorite_border_24)
                        true
                    } else {
                        binding.favoriteButton.setImageResource(R.drawable.ic_favorite_white_24)
                        false
                    }
                }
            }
        }

        binding.favoriteButton.setOnClickListener {
            isChecked = !isChecked
            if (isChecked) {
                if (username != null) {
                    detailViewModel.addFav(context, id, username, avatarURL)
                }
                binding.favoriteButton.setImageResource(R.drawable.ic_favorite_border_24)
            } else {
                detailViewModel.removeFav(context, id)
                binding.favoriteButton.setImageResource(R.drawable.ic_favorite_white_24)
            }
        }

        val sectionPagerAdapter = ViewPagerAdapter(this, supportFragmentManager, bundle)
        binding.apply {
            viewPager.adapter = sectionPagerAdapter
            tabLayout.setupWithViewPager(viewPager)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}