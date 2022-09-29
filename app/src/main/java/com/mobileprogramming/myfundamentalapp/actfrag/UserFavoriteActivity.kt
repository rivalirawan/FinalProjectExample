package com.mobileprogramming.myfundamentalapp.actfrag

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.mobileprogramming.myfundamentalapp.R
import com.mobileprogramming.myfundamentalapp.adapter.ListUserAdapter
import com.mobileprogramming.myfundamentalapp.databinding.ActivityUserFavoriteBinding
import com.mobileprogramming.myfundamentalapp.model.User
import com.mobileprogramming.myfundamentalapp.room.FavoriteEntity
import com.mobileprogramming.myfundamentalapp.viewmodel.FavoriteViewModel

class UserFavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUserFavoriteBinding
    private lateinit var favViewModel: FavoriteViewModel
    private lateinit var adapter: ListUserAdapter
    lateinit var context: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        context = this@UserFavoriteActivity

        supportActionBar?.title = "Favorite User"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        adapter = ListUserAdapter()
        adapter.notifyDataSetChanged()

        favViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(FavoriteViewModel::class.java)

        showRecyclerList()
        favViewModel.getFav(context)?.observe(this, Observer {
            if (it != null) {
                val listFav = mapList(it)
                adapter.setData(listFav)
            }
        })
    }

    private fun mapList(fav: List<FavoriteEntity>?): ArrayList<User> {
        val listFav = ArrayList<User>()
        if (fav != null) {
            for (user in fav) {
                val favMapped = User(
                    user.login,
                    user.id_user,
                    user.avatar_url
                )
                listFav.add(favMapped)
            }
        }
        return listFav
    }

    private fun showRecyclerList() {
        binding.rvUsers.setHasFixedSize(true)
        binding.rvUsers.layoutManager = LinearLayoutManager(this)
        binding.rvUsers.adapter = adapter
        adapter.setOnItemClickCallback(object : ListUserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: User) {
                Intent(this@UserFavoriteActivity, UserDetailActivity::class.java).also {
                    it.putExtra(UserDetailActivity.EXTRA_USERNAME, data.login)
                    it.putExtra(UserDetailActivity.EXTRA_ID, data.id)
                    it.putExtra(UserDetailActivity.EXTRA_AVATAR, data.avatar_url)
                    startActivity(it)
                }
            }
        })
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}