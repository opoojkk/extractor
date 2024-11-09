package com.joykeepsflowin.extractor.ui.home

import android.view.Menu
import android.view.MenuItem
import android.widget.PopupMenu
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.forEachIndexed
import androidx.recyclerview.widget.LinearLayoutManager
import com.joykeepsflowin.extractor.R
import com.joykeepsflowin.extractor.databinding.ActivityMainBinding
import com.joykeepsflowin.extractor.ui.home.appinfo.InstalledAppInfoAdapter
import com.joykeepsflowin.extractor.ui.home.appinfo.InstalledAppManager
import com.joykeepsflowin.extractor.kt.startActivity
import com.joykeepsflowin.extractor.kt.transparentNavigationBar
import com.joykeepsflowin.extractor.mvvm.MvvmActivity
import com.joykeepsflowin.extractor.ui.settings.SettingsActivity
import com.joykeepsflowin.lib_kv.Prefs

class MainActivity : MvvmActivity<ActivityMainBinding, AppInfoViewModel>() {

    override fun initData() {
        super.initData()
        val appInfos = InstalledAppManager.get(Prefs.get().getInt("filter_app_list_mode", 0) ?: 0)
        mBinding.rvInstalledApp.apply {
            adapter = InstalledAppInfoAdapter(appInfos.toMutableList())
            layoutManager = LinearLayoutManager(this@MainActivity)
        }

        mViewModel.observeFilterAppListMode(this)
        mViewModel.filterAppList.observe(this) { value ->
            mBinding.rvInstalledApp.apply {
                adapter = InstalledAppInfoAdapter(value.toMutableList())
                invalidate()
            }
        }
    }

    override fun initViews() {
        super.initViews()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, 0, systemBars.right, systemBars.bottom)
            insets
        }
        window.transparentNavigationBar()
        setSupportActionBar(mBinding.toolbar)
        supportActionBar?.title = resources.getString(R.string.app_name)
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menu?.let {
            menuInflater.inflate(R.menu.home_right_top_settings, it)
            return true
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.home_right_top_filter -> {
                showFilterPopupMenu()
                true
            }

            R.id.home_right_top_settings -> {
                startActivity<SettingsActivity>()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showFilterPopupMenu() {
        val popupMenu = PopupMenu(this, findViewById(R.id.home_right_top_filter))
        popupMenu.menuInflater.inflate(R.menu.home_right_top_popup, popupMenu.menu) // 另一个菜单的 XML 文件
        val selected = Prefs.get().getInt("filter_app_list_mode", 0) ?: 0
        popupMenu.menu.forEachIndexed { index, item ->
            if (index == selected) {
                item.isChecked = true
            }
        }

        popupMenu.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.home_popup_user -> {
                    Prefs.get().putInt("filter_app_list_mode", 0)
                    mViewModel.postFilterAppListModeValue(0)
                    true
                }

                R.id.home_popup_system -> {
                    Prefs.get().putInt("filter_app_list_mode", 1)
                    mViewModel.postFilterAppListModeValue(1)
                    true
                }

                R.id.home_popup_all -> {
                    Prefs.get().putInt("filter_app_list_mode", 2)
                    mViewModel.postFilterAppListModeValue(2)
                    true
                }

                else -> false
            }
        }

        popupMenu.show()
    }

}