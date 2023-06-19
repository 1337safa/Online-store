package com.safronov_original_app_online_store.presentation.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.safronov_original_app_online_store.R
import com.safronov_original_app_online_store.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

}