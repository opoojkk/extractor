package com.joykeepsflowin.extractor.home.appinfo.bean

import android.graphics.drawable.Drawable

data class AppInfo(
    // The application's name
    val appName: String,
    // The package name (unique identifier for the app)
    val packageName: String,
    // The application's icon
    val icon: Drawable,
    // The user-visible version name of the app (e.g., "1.0.0")
    val versionName: String?,
    // The internal version number of the app
    val versionCode: Int,
    // The timestamp of the first installation
    val firstInstallTime: Long,
    // The timestamp of the last update
    val lastUpdateTime: Long,
    // The file path of the APK
    val sourceDir: String,
    // The directory path where app data is stored
    val dataDir: String,
    // Whether the app is a system app
    val isSystemApp: Boolean,
    // Whether the system app has been updated
    val isUpdatedSystemApp: Boolean,
    // Whether the app is debuggable (usually development builds)
    val isDebuggable: Boolean,
    // The app's user ID (UID)
    val uid: Int,
    // The list of permissions requested by the app
    val permissions: List<String>,
)