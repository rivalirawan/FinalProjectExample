package com.mobileprogramming.myfundamentalapp.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = arrayOf(FavoriteEntity::class), version = 1, exportSchema = false)

abstract class UsersDatabase : RoomDatabase() {
    companion object {
        @Volatile
        var INSTANCE: UsersDatabase? = null

        fun getDatabase(context: Context): UsersDatabase? {
            if (INSTANCE == null) {
                synchronized(UsersDatabase::class) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        UsersDatabase::class.java,
                        "db_users"
                    ).build()
                }
            }
            return INSTANCE
        }
    }

    abstract fun favoriteDAO(): FavoriteDao
}