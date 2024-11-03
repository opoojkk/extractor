package com.joykeepsflowin.lib_kv

import android.content.Context
import android.os.Parcelable

interface PrefInterface {
    fun init(context: Context)

    fun putInt(key: String, value: Int): Boolean
    fun putLong(key: String, value: Long): Boolean
    fun putFloat(key: String, value: Float): Boolean
    fun putDouble(key: String, value: Double): Boolean
    fun putString(key: String, value: String): Boolean
    fun putBoolean(key: String, value: Boolean): Boolean
    fun putParcelable(key: String, value: Parcelable): Boolean

    fun getInt(key: String, fallback: Int): Int?
    fun getLong(key: String, fallback: Long): Long?
    fun getFloat(key: String, fallback: Float): Float?
    fun getDouble(key: String, fallback: Double): Double?
    fun getString(key: String, fallback: String): String?
    fun getBoolean(key: String, fallback: Boolean): Boolean?
    fun getParcelable(key: String, fallback: Parcelable): Parcelable?
}