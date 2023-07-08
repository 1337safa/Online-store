package com.safronov_original_app_online_store.presentation.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavHost
import androidx.navigation.ui.NavigationUI
import com.safronov_original_app_online_store.R
import com.safronov_original_app_online_store.core.extensions.logE
import com.safronov_original_app_online_store.databinding.ActivityMainBinding
import com.safronov_original_app_online_store.presentation.activity.communication_with_activity.bottom_nav_view.CommunicationWithBottomNavView

class MainActivity : AppCompatActivity(), CommunicationWithBottomNavView {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        try {
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

    override fun showBadgeForCartGraph(number: Int) {
        try {
            val badge = binding.allContentGraphNavigation.getOrCreateBadge(R.id.product_cart_graph)
            badge.number += number
            badge.backgroundColor = getColor(R.color.red)
            badge.isVisible = true
        } catch (e: Exception) {
            logE("${this.javaClass.name} -> ${object {}.javaClass.enclosingMethod?.name}, ${e.message}")
        }
    }

    override fun hideBadgeForCartGraph() {
        try {
            val badge = binding.allContentGraphNavigation.getBadge(R.id.product_cart_graph)
            badge?.number = 0
            badge?.isVisible = false
        } catch (e: Exception) {
            logE("${this.javaClass.name} -> ${object {}.javaClass.enclosingMethod?.name}, ${e.message}")
        }
    }

}