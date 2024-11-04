package com.joykeepsflowin.extractor

import android.app.Application
import com.joykeepsflowin.extractor.home.appinfo.InstalledAppManager
import com.joykeepsflowin.lib_kv.Prefs

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        initComponents()
    }

    private fun initComponents() {
        InstalledAppManager.init(this)
        Prefs.init(this, object : Prefs.Config() {})
    }
}