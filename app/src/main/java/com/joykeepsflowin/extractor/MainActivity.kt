package com.joykeepsflowin.extractor

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.joykeepsflowin.extractor.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var mainBinding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
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

        val appInfos = getInstalledApps(false)
        mainBinding.rvInstalledApp.apply {
            adapter = InstalledAppInfoAdapter(appInfos)
            layoutManager = LinearLayoutManager(this@MainActivity)
        }

    }


}