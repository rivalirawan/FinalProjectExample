package com.mobileprogramming.myfundamentalapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mobileprogramming.myfundamentalapp.api.ApiConfig
import com.mobileprogramming.myfundamentalapp.model.User
import com.mobileprogramming.myfundamentalapp.model.UsersList
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {
    private val listUsers = MutableLiveData<ArrayList<User>>()

    fun setUsers(username: String) {
        ApiConfig.apiInstance
            .getUsersList(username)
            .enqueue(object : Callback<UsersList> {
                override fun onResponse(
                    call: Call<UsersList>,
                    response: Response<UsersList>
                ) {
                    if (response.isSuccessful) {
                        listUsers.postValue(response.body()?.items)
                    }
                }

                override fun onFailure(call: Call<UsersList>, t: Throwable) {
                    Log.d("Failure", t.message.toString())
                }
            })
    }

    fun getUsers(): LiveData<ArrayList<User>> {
        return listUsers
    }
}