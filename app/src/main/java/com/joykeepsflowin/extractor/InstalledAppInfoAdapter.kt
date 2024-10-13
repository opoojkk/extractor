package com.joykeepsflowin.extractor

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.joykeepsflowin.extractor.databinding.ItemInstalledAppBinding
import java.io.File

class InstalledAppInfoAdapter(private val appList: List<AppInfo>) :
    RecyclerView.Adapter<InstalledAppInfoAdapter.AppViewHolder>() {

    class AppViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var mBinding: ItemInstalledAppBinding = ItemInstalledAppBinding.bind(view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_installed_app, parent, false)
        return AppViewHolder(view)
    }

    override fun onBindViewHolder(holder: AppViewHolder, position: Int) {
        val app = appList[position]
        holder.mBinding.ivAppIcon.setImageDrawable(app.icon)
        holder.mBinding.tvAppName.text = app.appName
        holder.mBinding.tvAppPackage.text = app.packageName
        holder.mBinding.tvAppVersion.text = app.versionName
        holder.itemView.setOnClickListener {
            val context = holder.itemView.context
            val path =
                context.filesDir.absolutePath + File.separator + app.appName + "_${System.currentTimeMillis()}.apk"
            val result = extractInstalledAppApk(context, app.packageName, path)
            if (!result.isNullOrEmpty()) {
                Toast.makeText(context, "success, path:$result", Toast.LENGTH_SHORT).show()
            }
        }

    }

    override fun getItemCount() = appList.size
}
