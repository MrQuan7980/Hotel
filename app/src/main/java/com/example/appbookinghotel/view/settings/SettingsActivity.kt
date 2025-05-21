package com.example.appbookinghotel.view.settings

import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.example.core.utils.openActivity
import com.example.core.local.PerformDataStore
import androidx.appcompat.app.AppCompatActivity
import com.example.appbookinghotel.view.auth.SignInActivity
import com.example.appbookinghotel.databinding.ActivitySettingsBinding

class SettingsActivity : AppCompatActivity() {
    private lateinit var binding : ActivitySettingsBinding
    private val dataStore : PerformDataStore by lazy {
        PerformDataStore(applicationContext)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        eventHandling()
    }
    private fun eventHandling()
    {
        lifecycleScope.launchWhenStarted {
            dataStore.getString(PerformDataStore.KEY_NAME).collect{name ->

            }
        }
        binding.btnLogout.setOnClickListener {
            lifecycleScope.launchWhenStarted {
                dataStore.clear()
                openActivity<SignInActivity>("Đăng xuất...")
            }
        }

    }
}