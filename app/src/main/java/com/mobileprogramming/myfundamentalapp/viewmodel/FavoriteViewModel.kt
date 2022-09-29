package com.mobileprogramming.myfundamentalapp.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.mobileprogramming.myfundamentalapp.repository.UsersRepository
import com.mobileprogramming.myfundamentalapp.room.FavoriteEntity

class FavoriteViewModel: ViewModel() {
    private var dataFav: LiveData<List<FavoriteEntity>>? = null

    fun getFav(context: Context) : LiveData<List<FavoriteEntity>>?{
        dataFav = UsersRepository.getFav(context)
        return dataFav
    }
}