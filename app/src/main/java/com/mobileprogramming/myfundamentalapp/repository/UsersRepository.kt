package com.mobileprogramming.myfundamentalapp.repository

import android.content.Context
import android.database.Cursor
import androidx.lifecycle.LiveData
import com.mobileprogramming.myfundamentalapp.room.FavoriteEntity
import com.mobileprogramming.myfundamentalapp.room.UsersDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class UsersRepository {
    companion object {
        var userDatabase: UsersDatabase? = null
        var userFavorite: LiveData<List<FavoriteEntity>>? = null
        var favUsers: Cursor? = null

        fun initializeDB(context: Context): UsersDatabase? {
            return UsersDatabase.getDatabase(context)
        }

        fun getFav(context: Context): LiveData<List<FavoriteEntity>>? {
            userDatabase = initializeDB(context)
            userFavorite = userDatabase?.favoriteDAO()?.getAll()
            return userFavorite
        }

        fun addFav(context: Context, id: Int, login: String, avatar: String) {

            userDatabase = initializeDB(context)

            CoroutineScope(IO).launch {
                val data = FavoriteEntity(login, id, avatar)
                userDatabase?.favoriteDAO()?.add(data)
            }

        }

        fun checkUser(context: Context, id: Int): Int? {
            userDatabase = initializeDB(context)
            return userDatabase?.favoriteDAO()?.checkUser(id)
        }

        fun removeFav(context: Context, id: Int) {
            userDatabase = initializeDB(context)

            CoroutineScope(IO).launch {
                userDatabase?.favoriteDAO()?.delete(id)
            }
        }

        fun getAll(context: Context): Cursor? {
            userDatabase = initializeDB(context)
            favUsers = userDatabase?.favoriteDAO()?.getFavs()
            return favUsers
        }
    }
}