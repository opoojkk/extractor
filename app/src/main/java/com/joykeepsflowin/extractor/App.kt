package com.joykeepsflowin.extractor

import android.app.Application
import com.joykeepsflowin.lib_kv.Prefs

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        initComponents()
    }

    private fun initComponents() {
        Prefs.init(this, object : Prefs.Config() {})
    }
}