package com.dms.imagesearch

import android.app.Application
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class ImageSearchApp : Application() {

    override fun onCreate() {
        super.onCreate()
    }
}