package com.joykeepsflowin.extractor.bean

import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import android.util.Log

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


fun Context.getInstalledApps(filerSystemApp: Boolean = true): List<AppInfo> {
    val packageManager: PackageManager = packageManager
    val apps = packageManager.getInstalledApplications(PackageManager.GET_META_DATA)

    val appInfoList = mutableListOf<AppInfo>()

    for (app in apps) {
        // filter system app
        if (filerSystemApp && (app.flags and ApplicationInfo.FLAG_SYSTEM) != 0) {
            Log.d("AppInfo", "system app, next")
            continue
        }
        val packageName = app.packageName
        val appInfo = packageManager.getApplicationInfo(packageName, 0)
        val packageInfo = packageManager.getPackageInfo(packageName, PackageManager.GET_PERMISSIONS)

        // create AppInfo for it
        appInfoList.add(
            AppInfo(
                appName = packageManager.getApplicationLabel(appInfo).toString(),
                packageName = packageInfo.packageName,
                icon = packageManager.getApplicationIcon(appInfo.packageName),
                versionName = packageInfo.versionName,
                versionCode = packageInfo.versionCode,
                firstInstallTime = packageInfo.firstInstallTime,
                lastUpdateTime = packageInfo.lastUpdateTime,
                sourceDir = appInfo.sourceDir,
                dataDir = appInfo.dataDir,
                isSystemApp = (appInfo.flags and ApplicationInfo.FLAG_SYSTEM) != 0,
                isUpdatedSystemApp = (appInfo.flags and ApplicationInfo.FLAG_UPDATED_SYSTEM_APP) != 0,
                isDebuggable = (appInfo.flags and ApplicationInfo.FLAG_DEBUGGABLE) != 0,
                uid = appInfo.uid,
                permissions = packageInfo.requestedPermissions?.toList() ?: emptyList(),
            )
        )
    }

    return appInfoList
}
