package com.safronov_original_app_online_store.presentation.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.navigation.NavController
import androidx.navigation.NavHost
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.safronov_original_app_online_store.R
import com.safronov_original_app_online_store.core.extensions.logE
import com.safronov_original_app_online_store.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        try {
            binding.mainActionBar.setNavigationIcon(resources.getDrawable(R.drawable.ic_back))
            initMainGraph()
        } catch (e: Exception) {
            logE("${this.javaClass.name} -> ${object{}.javaClass.enclosingMethod?.name}, ${e.message}")
        }
    }

    private fun initMainGraph() {
        val navHost = supportFragmentManager.findFragmentById(R.id.main_fragment_container) as NavHost
        val navController = navHost.navController
        NavigationUI.setupWithNavController(binding.mainActionBar, navController)
        NavigationUI.setupWithNavController(binding.allContentGraphNavigation, navController)
    }

}