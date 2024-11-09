package com.joykeepsflowin.extractor.ui.home.appinfo

import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import com.joykeepsflowin.extractor.ui.home.appinfo.bean.AppInfo

object InstalledAppManager {
    private val userAppInfoList = mutableListOf<AppInfo>()
    private val systemAppInfoList = mutableListOf<AppInfo>()

    fun init(context: Context) {
        val pair = getInstalledApps(context)
        userAppInfoList.addAll(pair.first)
        systemAppInfoList.addAll(pair.second)
    }

    fun get(mode: Int) = when (mode) {
        InstalledAppMode.USER.value -> userAppInfoList
        InstalledAppMode.SYSTEM.value -> systemAppInfoList
        InstalledAppMode.ALL.value -> userAppInfoList + systemAppInfoList
        else -> {
            throw IllegalArgumentException("it should be a member of InstalledAppMode")
        }
    }

    private fun getInstalledApps(
        context: Context,
    ): Pair<List<AppInfo>, List<AppInfo>> {
        val packageManager: PackageManager = context.packageManager
        val apps = packageManager.getInstalledApplications(PackageManager.GET_META_DATA)

        val userAppInfoList = mutableListOf<AppInfo>()
        val systemAppInfoList = mutableListOf<AppInfo>()

        for (app in apps) {
            val packageName = app.packageName
            val appInfo = packageManager.getApplicationInfo(packageName, 0)
            val packageInfo =
                packageManager.getPackageInfo(packageName, PackageManager.GET_PERMISSIONS)
            // filter system app
            if ((app.flags and ApplicationInfo.FLAG_SYSTEM) != 0) {
                systemAppInfoList.add(
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
                continue
            } else {
                userAppInfoList.add(
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
        }
        return Pair(userAppInfoList, systemAppInfoList)
    }
}