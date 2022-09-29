package com.mobileprogramming.myfundamentalapp.adapter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mobileprogramming.myfundamentalapp.preferences.SettingPreferences
import com.mobileprogramming.myfundamentalapp.viewmodel.ThemeChangerViewModel

class ThemeViewModelFactory(private val pref: SettingPreferences) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ThemeChangerViewModel::class.java)) {
            return ThemeChangerViewModel(pref) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}