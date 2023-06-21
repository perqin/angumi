package com.perqin.angumi

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.perqin.angumi.data.settings.SettingsRepo
import com.perqin.angumi.databinding.ActivityMainBinding
import com.perqin.angumi.ui.home.HomeFragmentDirections
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_container_view)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
//        val appBarConfiguration = AppBarConfiguration(setOf(
//                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications))
//        setupActionBarWithNavController(navController, appBarConfiguration)
//        navView.setupWithNavController(navController)

        lifecycleScope.launch {
            if (!SettingsRepo.flags.first().newToSignIn) {
                navController.navigate(HomeFragmentDirections.globalAuthAction())
            }
        }
    }
}
