package com.androidtech.data.local.prefs

import android.content.Context
import androidx.core.content.edit
import com.androidtech.data.R
import kotlin.reflect.KProperty

abstract class Preference(
    private val context: Context
) {

    private val prefs = context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)

    enum class StorageType {
        String, Integer, Float, Long, Boolean
    }

    interface PreferenceDelegate<T> {
        operator fun setValue(thisReference: Any, property: KProperty<*>, value: T)
        operator fun getValue(thisReference: Any, property: KProperty<*>): T
    }

    @Suppress("UNCHECKED_CAST")
    inner class GenericDelegate<T>(
        private val prefKey: String,
        private val prefValue: T,
        private val storageType: StorageType
    ): PreferenceDelegate<T> {
        override fun setValue(thisReference: Any, property: KProperty<*>, value: T) {
            when (storageType) {
                StorageType.String -> prefs.edit(true) { putString(prefKey, value as String) }
                StorageType.Integer -> prefs.edit(true) { putInt(prefKey, value as Int) }
                StorageType.Float -> prefs.edit(true) { putFloat(prefKey, value as Float) }
                StorageType.Long -> prefs.edit(true) { putLong(prefKey, value as Long) }
                StorageType.Boolean -> prefs.edit(true) { putBoolean(prefKey, value as Boolean) }
            }
        }

        override fun getValue(thisReference: Any, property: KProperty<*>): T {
            return when (storageType) {
                StorageType.String -> prefs.getString(prefKey, prefValue as String) as T
                StorageType.Integer -> prefs.getInt(prefKey, prefValue as Int) as T
                StorageType.Float -> prefs.getFloat(prefKey, prefValue as Float) as T
                StorageType.Long -> prefs.getLong(prefKey, prefValue as Long) as T
                StorageType.Boolean -> prefs.getBoolean(prefKey, prefValue as Boolean) as T
            }
        }
    }

    fun stringRef(
        prefKey: String,
        defaultValue: String
    ) = GenericDelegate(prefKey, defaultValue, StorageType.String)

    fun intRef(
        prefKey: String,
        defaultValue: Int
    ) = GenericDelegate(prefKey, defaultValue, StorageType.Integer)

    fun floatRef(
        prefKey: String,
        defaultValue: Float
    ) = GenericDelegate(prefKey, defaultValue, StorageType.Float)

    fun longRef(
        prefKey: String,
        defaultValue: Long
    ) = GenericDelegate(prefKey, defaultValue, StorageType.Long)

    fun booleanRef(
        prefKey: String,
        defaultValue: Boolean
    ) = GenericDelegate(prefKey, defaultValue, StorageType.Boolean)
}