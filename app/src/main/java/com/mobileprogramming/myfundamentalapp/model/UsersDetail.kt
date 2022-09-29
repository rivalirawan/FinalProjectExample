package com.mobileprogramming.myfundamentalapp.model

data class UsersDetail(
    val login: String,
    val name: String,
    val avatar_url: String,
    val followers_url: String,
    val following_url: String,
    val company: String,
    val location: String,
    val followers: Int,
    val following: Int,
    val public_repos: Int
)
