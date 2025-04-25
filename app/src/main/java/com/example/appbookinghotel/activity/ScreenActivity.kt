package com.example.appbookinghotel.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.appbookinghotel.databinding.ActivityScreenBinding
import com.example.appbookinghotel.view.SignInActivity

class ScreenActivity : AppCompatActivity() {
    private lateinit var binding : ActivityScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        eventHandling()
    }
    private fun eventHandling()
    {
        binding.pageChange.setOnClickListener {
            startActivity(Intent(this, SignInActivity::class.java))
        }
    }
}