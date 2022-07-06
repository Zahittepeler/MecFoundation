package com.halasteknoloji.meckurbanyonetim.base

import android.app.Application
import com.halasteknoloji.meckurbanyonetim.utils.LocalDataManager

class BaseApp: Application() {

    override fun onCreate() {
        super.onCreate()
        LocalDataManager.init(context = applicationContext)
    }
}