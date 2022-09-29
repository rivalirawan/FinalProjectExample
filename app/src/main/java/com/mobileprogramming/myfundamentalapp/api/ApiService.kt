package com.mobileprogramming.myfundamentalapp.api

import com.mobileprogramming.myfundamentalapp.model.User
import com.mobileprogramming.myfundamentalapp.model.UsersDetail
import com.mobileprogramming.myfundamentalapp.model.UsersList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("search/users")
    fun getUsersList(@Query("q") query: String): Call<UsersList>

    @GET("users/{username}")
    fun getUsersDetail(@Path("username") username: String): Call<UsersDetail>

    @GET("users/{username}/followers")
    fun getUsersFollowers(@Path("username") username: String): Call<ArrayList<User>>

    @GET("users/{username}/following")
    fun getUsersFollowing(@Path("username") username: String): Call<ArrayList<User>>
}