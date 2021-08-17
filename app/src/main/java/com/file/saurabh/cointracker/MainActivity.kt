package com.file.saurabh.cointracker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.file.saurabh.cointracker.databinding.ActivityMainBinding
import com.file.saurabh.cointracker.utils.MainActivityUtils
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.util.*

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding  = DataBindingUtil.setContentView(this,R.layout.activity_main)

        val bottomNavigationView = binding.bottomNavView
        val navController = findNavController(R.id.nav_host_fragment)
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
}