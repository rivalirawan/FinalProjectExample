package com.mobileprogramming.myfundamentalapp.service

import android.content.Intent
import android.widget.RemoteViewsService
import com.mobileprogramming.myfundamentalapp.adapter.StackRemoteViewsFactory

class StackWidgetService : RemoteViewsService() {
    override fun onGetViewFactory(intent: Intent): RemoteViewsFactory =
        StackRemoteViewsFactory(this.applicationContext)
}