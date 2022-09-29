package com.mobileprogramming.myfundamentalapp.room

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "users")
data class FavoriteEntity(
    val login: String,

    @PrimaryKey
    var id_user: Int,

    val avatar_url:String
) : Serializable
