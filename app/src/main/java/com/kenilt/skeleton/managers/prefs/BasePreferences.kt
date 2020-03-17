package com.kenilt.skeleton.managers.prefs

import android.content.Context
import android.content.SharedPreferences

/**
 * Created by thangnguyen on 12/21/18.
 */
abstract class BasePreferences {
    private lateinit var prefs: SharedPreferences

    protected fun init(context: Context, tableName: String) {
        this.prefs = context.getSharedPreferences(tableName, Context.MODE_PRIVATE)
    }

    fun getString(key: String, value: String? = null): String? = prefs.getString(key, value)

    fun putString(key: String, value: String?) {
        prefs.edit().putString(key, value).apply()
    }

    fun has(key: String): Boolean {
        return prefs.contains(key)
    }

    fun getInt(key: String, defValue: Int): Int = prefs.getInt(key, defValue)

    fun putInt(key: String, value: Int) {
        prefs.edit().putInt(key, value).apply()
    }

    fun getLong(key: String, defValue: Long): Long = prefs.getLong(key, defValue)

    fun putLong(key: String, value: Long) {
        prefs.edit().putLong(key, value).apply()
    }

    fun removeKey(key: String) {
        prefs.edit().remove(key).apply()
    }
}
