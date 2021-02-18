package com.wgoweb.kokaibywgo.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.wgoweb.kokaibywgo.R
import com.wgoweb.kokaibywgo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val navController = Navigation.findNavController(this, R.id.nav_host_fragment)


        when(resources.configuration.orientation) {
            //portrait
            1 -> {
                binding.toolbar.visibility = View.GONE
            }
            //landscape
            2 -> {
                setSupportActionBar(binding.toolbar)  // show to bar menu
                setupActionBar(navController) // show to bar menu
            }
        }
        setupBottomNavMenu(navController)
        setupSideNavigationMenu(navController)

    }

    private fun setupBottomNavMenu(navController: NavController) {
        binding.bottomNav?.let {
            NavigationUI.setupWithNavController(it, navController)
        }
    }

    private fun setupSideNavigationMenu(navController: NavController) {
        binding.navView?.let {
            NavigationUI.setupWithNavController(it, navController)
        }
    }

    private fun setupActionBar(navController: NavController) {
        when(resources.configuration.orientation) {
            //portrait
            1 -> NavigationUI.setupActionBarWithNavController(this, navController)
            //landscape
            2 -> NavigationUI.setupActionBarWithNavController(this, navController, binding.drawerLayout)
            else -> NavigationUI.setupActionBarWithNavController(this, navController)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_toolbar, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        val navigated = NavigationUI.onNavDestinationSelected(item, navController)
        return navigated || super.onOptionsItemSelected(item)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        return NavigationUI.navigateUp(navController, binding.drawerLayout)

    }
}