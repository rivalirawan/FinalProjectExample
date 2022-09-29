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
    @Headers("Authorization: token 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")
    fun getUsersList(@Query("q") query: String): Call<UsersList>

    @GET("users/{username}")
    @Headers("Authorization: token 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")
    fun getUsersDetail(@Path("username") username: String): Call<UsersDetail>

    @GET("users/{username}/followers")
    @Headers("Authorization: token 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")
    fun getUsersFollowers(@Path("username") username: String): Call<ArrayList<User>>

    @GET("users/{username}/following")
    @Headers("Authorization: token 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")
    fun getUsersFollowing(@Path("username") username: String): Call<ArrayList<User>>
}