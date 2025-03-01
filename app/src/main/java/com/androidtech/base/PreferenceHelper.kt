package com.androidtech.base

import android.content.Context
import com.androidtech.app.MainApplication
import com.androidtech.data.local.prefs.Preference
import java.lang.ref.WeakReference

class PreferenceHelper(
    private val context: Context
): Preference(context) {

    var demoName by stringRef("demo_name", "Demo Name")

    companion object {
        private var instance: WeakReference<PreferenceHelper>? = null

        fun getInstance(): PreferenceHelper {
            if (instance?.get() == null) instance = WeakReference(PreferenceHelper(MainApplication.getInstance().applicationContext))
            return instance?.get()!!
        }
    }
}