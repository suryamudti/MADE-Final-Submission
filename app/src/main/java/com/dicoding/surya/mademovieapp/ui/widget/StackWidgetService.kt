package com.dicoding.surya.mademovieapp.ui.widget

import android.content.Intent
import android.widget.RemoteViewsService

/**
 * Created by suryamudti on 29/08/2019.
 */
class StackWidgetService : RemoteViewsService() {
    override fun onGetViewFactory(intent: Intent?): RemoteViewsFactory {
        return StackRemoteViewFactory(this.applicationContext)
    }
}