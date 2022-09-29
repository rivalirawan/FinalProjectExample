package com.mobileprogramming.myfundamentalapp.adapter

import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.graphics.Bitmap
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import androidx.core.os.bundleOf
import com.bumptech.glide.Glide
import com.mobileprogramming.myfundamentalapp.R
import com.mobileprogramming.myfundamentalapp.model.User
import com.mobileprogramming.myfundamentalapp.repository.UsersRepository
import com.mobileprogramming.myfundamentalapp.viewmodel.FavoriteViewModel
import com.mobileprogramming.myfundamentalapp.viewmodel.UserFavoriteWidget

internal class StackRemoteViewsFactory(private val context: Context) :
    RemoteViewsService.RemoteViewsFactory {

    private var listUser = ArrayList<User>()
    private lateinit var favViewModel: FavoriteViewModel

    override fun onCreate() {}

    override fun onDataSetChanged() {
        var cursor = context.let { UsersRepository.getAll(it) }
        listUser = mapList(cursor)
    }

    private fun mapList(fav: Cursor?): ArrayList<User> {
        val listFav = ArrayList<User>()
        if (fav != null) {
            while (fav.moveToNext()) {
                val username = fav.getString(fav.getColumnIndexOrThrow("login"))
                val id = fav.getInt(fav.getColumnIndexOrThrow("id"))
                val avatar = fav.getString(fav.getColumnIndexOrThrow("avatar_url"))
                val user = User(username, id, avatar)
                listFav.add(user)
            }
        }
        return listFav
    }

    override fun onDestroy() {
        listUser.clear()
    }

    override fun getCount(): Int = listUser.size

    override fun getViewAt(i: Int): RemoteViews {

        val rv = RemoteViews(context.packageName, R.layout.item_widget)
        try {
            val bitmap: Bitmap = Glide.with(context)
                .asBitmap()
                .load(listUser[i].avatar_url)
                .submit(512, 512)
                .get()
            rv.setImageViewBitmap(R.id.img_avatar, bitmap)
            rv.setTextViewText(R.id.tv_item_username, listUser[i].login)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        val extras = bundleOf(
            UserFavoriteWidget.EXTRA_ITEM to i
        )

        val fillInIntent = Intent()
        fillInIntent.putExtras(extras)
        rv.setOnClickFillInIntent(R.layout.item_widget, fillInIntent)
        return rv
    }

    override fun getLoadingView(): RemoteViews? = null

    override fun getViewTypeCount(): Int = 1

    override fun getItemId(i: Int): Long = 0

    override fun hasStableIds(): Boolean = false

}