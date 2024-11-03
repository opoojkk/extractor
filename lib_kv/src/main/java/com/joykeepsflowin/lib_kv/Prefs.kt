package com.joykeepsflowin.lib_kv

import android.content.Context
import android.util.Log
import java.util.concurrent.atomic.AtomicBoolean

object Prefs {
    const val TAG = "Prefs"

    private val init: AtomicBoolean = AtomicBoolean(false)

    private var config: Config = object : Config() {}
    private lateinit var pref: PrefInterface

    fun init(context: Context, config: Config) {
        if (init.get()) {
            Log.w(TAG, "has inited")
            return
        }
        this.config = config
        pref = when (this.config.getImplementType()) {
            Type.MMKV -> MMKVPerf()
            else -> throw NotImplementedError("not supported type")
        }
        pref.init(context)
        init.set(true)
    }

    fun get(): PrefInterface {
        return pref
    }

    private fun checkInit() {
        if (!init.get()) {
            throw IllegalAccessException("you have to call init before you use it.")
        }
    }

    enum class Type {
        SharedPreferences, MMKV
    }

    abstract class Config {
        open fun getImplementType(): Type {
            return Type.MMKV
        }
    }
}