package com.joykeepsflowin.lib_kv

import android.content.Context
import android.os.Parcelable
import com.tencent.mmkv.MMKV
import com.tencent.mmkv.MMKV.SINGLE_PROCESS_MODE

class MMKVPerf : PrefInterface {
    private lateinit var obj: MMKV
    override fun init(context: Context) {
        MMKV.initialize(context)
        obj = MMKV.mmkvWithID(context.packageName.replace(".", "_"), SINGLE_PROCESS_MODE)
    }

    override fun putInt(key: String, value: Int): Boolean {
        return obj.encode(key, value)
    }

    override fun putLong(key: String, value: Long): Boolean {
        return obj.encode(key, value)
    }

    override fun putFloat(key: String, value: Float): Boolean {
        return obj.encode(key, value)
    }

    override fun putDouble(key: String, value: Double): Boolean {
        return obj.encode(key, value)
    }

    override fun putString(key: String, value: String): Boolean {
        return obj.encode(key, value)
    }

    override fun putBoolean(key: String, value: Boolean): Boolean {
        return obj.encode(key, value)
    }

    override fun putParcelable(key: String, value: Parcelable): Boolean {
        return obj.encode(key, value)
    }

    override fun getInt(key: String, fallback: Int): Int? {
        return obj.decodeInt(key, fallback)
    }

    override fun getLong(key: String, fallback: Long): Long? {
        return obj.decodeLong(key, fallback)
    }

    override fun getFloat(key: String, fallback: Float): Float? {
        return obj.decodeFloat(key, fallback)
    }

    override fun getDouble(key: String, fallback: Double): Double? {
        return obj.decodeDouble(key, fallback)
    }

    override fun getString(key: String, fallback: String): String? {
        return obj.decodeString(key, fallback)
    }

    override fun getBoolean(key: String, fallback: Boolean): Boolean? {
        return obj.decodeBool(key, fallback)
    }

    override fun getParcelable(key: String, fallback: Parcelable): Parcelable? {
        return obj.decodeParcelable(key, fallback.javaClass)
    }
}