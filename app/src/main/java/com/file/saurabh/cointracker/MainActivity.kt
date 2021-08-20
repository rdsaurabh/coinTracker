package com.file.saurabh.cointracker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController

import com.file.saurabh.cointracker.databinding.ActivityMainBinding
import com.file.saurabh.cointracker.utils.MainActivityUtils



class MainActivity : AppCompatActivity() {
    private  lateinit var binding: ActivityMainBinding
    private  lateinit var navController : NavController


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding  = DataBindingUtil.setContentView(this,R.layout.activity_main)

        val bottomNavigationView = binding.bottomNavView
        navController = findNavController(R.id.nav_host_fragment)
        bottomNavigationView.setupWithNavController(navController)
        setSupportActionBar(binding.toolbar)



        /*
          * setting appbar with navigation controller using appbar config which contains id of
          * layouts whose name has to be shown on appbar.Nav Controller will show
          * name of view on appbar which is being currently shown to user and changes as
          * user navigates away from from the screen.
          *  */
        val appBarConfiguration = AppBarConfiguration(MainActivityUtils().getAppConfigSet())
        setupActionBarWithNavController(navController,appBarConfiguration)
    }

    /*makes up button in appbar work when user is deeper in view hierarchy except home screens*/
    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController,null)
    }

}