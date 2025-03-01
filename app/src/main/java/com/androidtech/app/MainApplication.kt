package com.androidtech.app

import android.app.Application
import com.androidtech.util.Logger
import dagger.hilt.android.HiltAndroidApp


/**
 * Main application
 * Chú thích @HiltAndroidApp chỉ Dagger-Hilt biết thời điểm khởi tạo Component chưa tất cả các modules của DI
 * (Ngay khi ứng dụng được chạy)
 * @constructor Create empty Main application
 */
@HiltAndroidApp
class MainApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this

        // khởi tạo Logger với thư viện Timber
        Logger.init()
    }

    companion object {
        private var instance: MainApplication? = null

        fun getInstance(): MainApplication {
            if (instance == null) instance = MainApplication()
            return instance!!
        }
    }
}