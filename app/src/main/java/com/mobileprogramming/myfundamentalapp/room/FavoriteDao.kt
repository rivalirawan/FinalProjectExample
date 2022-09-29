package com.mobileprogramming.myfundamentalapp.room

import android.database.Cursor
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface FavoriteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun add(users: FavoriteEntity)

    @Query("SELECT * FROM users")
    fun getAll(): LiveData<List<FavoriteEntity>>

    @Query("DELETE FROM users WHERE id_user = :id ")
    fun delete(id: Int): Int

    @Query("SELECT COUNT(id_user) FROM users WHERE id_user = :id")
    fun checkUser(id: Int): Int

    @Query("SELECT * FROM users")
    fun getFavs(): Cursor
}