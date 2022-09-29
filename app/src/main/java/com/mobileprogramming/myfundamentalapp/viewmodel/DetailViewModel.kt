package com.mobileprogramming.myfundamentalapp.viewmodel

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mobileprogramming.myfundamentalapp.api.ApiConfig
import com.mobileprogramming.myfundamentalapp.model.UsersDetail
import com.mobileprogramming.myfundamentalapp.repository.UsersRepository
import com.mobileprogramming.myfundamentalapp.room.FavoriteDao
import com.mobileprogramming.myfundamentalapp.room.UsersDatabase
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel(app: Application) : AndroidViewModel(app) {
    private var favDao: FavoriteDao?
    private var db: UsersDatabase? = UsersDatabase.getDatabase(app)
    val detailUser = MutableLiveData<UsersDetail>()

    init {
        favDao = db?.favoriteDAO()
    }

    fun setUser(username: String) {
        ApiConfig.apiInstance
            .getUsersDetail(username)
            .enqueue(object : Callback<UsersDetail> {
                override fun onResponse(call: Call<UsersDetail>, response: Response<UsersDetail>) {
                    if (response.isSuccessful) {
                        detailUser.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<UsersDetail>, t: Throwable) {
                    Log.d("Failure", t.message.toString())
                }
            })
    }

    fun getUser(): LiveData<UsersDetail> {
        return detailUser
    }

    fun addFav(context: Context, id: Int, login: String, avatar: String) {
        UsersRepository.addFav(context,id,login, avatar)
    }

    fun checkUser(context: Context, id: Int): Int? {
        return UsersRepository.checkUser(context, id)
    }

    fun removeFav(context: Context, id: Int) {
        UsersRepository.removeFav(context, id)
    }
}