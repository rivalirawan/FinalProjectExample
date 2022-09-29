package com.mobileprogramming.myfundamentalapp.provider

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import com.mobileprogramming.myfundamentalapp.repository.UsersRepository
import com.mobileprogramming.myfundamentalapp.room.FavoriteDao
import com.mobileprogramming.myfundamentalapp.room.UsersDatabase

class UsersContentProvider : ContentProvider() {
    private lateinit var favDao: FavoriteDao

    companion object {
        const val AUTHORITY_URI = "com.mobileprogramming.myfundamentalapp"
        const val TABLE_NAME = "users"
        const val ID_DATA_USER = 1
        val uriMatcher = UriMatcher(UriMatcher.NO_MATCH)

        init {
            uriMatcher.addURI(AUTHORITY_URI, TABLE_NAME, ID_DATA_USER)
        }
    }

    override fun onCreate(): Boolean {
        favDao = context?.let {
            UsersDatabase.getDatabase(it)?.favoriteDAO()
        }!!
        return false
    }

    override fun getType(uri: Uri): String? {
        return null
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        return null
    }

    override fun query(
        uri: Uri, projection: Array<String>?, selection: String?,
        selectionArgs: Array<String>?, sortOrder: String?
    ): Cursor? {
        var cursor: Cursor?
        when (uriMatcher.match(uri)) {
            ID_DATA_USER -> {
                cursor = context?.let { UsersRepository.getAll(it) }
                if (context != null) {
                    cursor?.setNotificationUri(context?.contentResolver, uri)
                }
            }
            else -> {
                cursor = null
            }
        }
        return cursor
    }

    override fun update(
        uri: Uri, values: ContentValues?, selection: String?,
        selectionArgs: Array<String>?
    ): Int {
        return 0
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        return 0
    }
}