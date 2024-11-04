package com.joykeepsflowin.extractor.home

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.PopupMenu
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.forEachIndexed
import androidx.recyclerview.widget.LinearLayoutManager
import com.joykeepsflowin.extractor.R
import com.joykeepsflowin.extractor.databinding.ActivityMainBinding
import com.joykeepsflowin.extractor.home.appinfo.InstalledAppInfoAdapter
import com.joykeepsflowin.extractor.home.appinfo.InstalledAppManager
import com.joykeepsflowin.extractor.kt.transparentNavigationBar
import com.joykeepsflowin.lib_kv.Prefs

class MainActivity : AppCompatActivity() {
    private lateinit var mainBinding: ActivityMainBinding
    private val appInfoViewModel: AppInfoViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        initViews()
        initData()
    }

    private fun initData() {
        val appInfos = InstalledAppManager.get(Prefs.get().getInt("filter_app_list_mode", 0) ?: 0)
        mainBinding.rvInstalledApp.apply {
            adapter = InstalledAppInfoAdapter(appInfos.toMutableList())
            layoutManager = LinearLayoutManager(this@MainActivity)
        }

        appInfoViewModel.observeFilterAppListMode(this)
        appInfoViewModel.filterAppList.observe(this) { value ->
            mainBinding.rvInstalledApp.apply {
                adapter = InstalledAppInfoAdapter(value.toMutableList())
                invalidate()
            }
        }
    }

    private fun initViews() {
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, 0, systemBars.right, systemBars.bottom)
            insets
        }
        window.transparentNavigationBar()
        setSupportActionBar(mainBinding.toolbar)
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

            R.menu.home_right_top_settings -> {
                Toast.makeText(this, "not implement yet", Toast.LENGTH_SHORT).show()
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
                    appInfoViewModel.postFilterAppListModeValue(0)
                    true
                }

                R.id.home_popup_system -> {
                    Prefs.get().putInt("filter_app_list_mode", 1)
                    appInfoViewModel.postFilterAppListModeValue(1)
                    true
                }

                R.id.home_popup_all -> {
                    Prefs.get().putInt("filter_app_list_mode", 2)
                    appInfoViewModel.postFilterAppListModeValue(2)
                    true
                }

                else -> false
            }
        }

        popupMenu.show()
    }

}