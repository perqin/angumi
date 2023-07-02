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
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val settingsRepo: SettingsRepo by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navController = findNavController(R.id.nav_host_fragment_container_view)

        lifecycleScope.launch {
            if (!settingsRepo.flags.first().signInLater) {
                navController.navigate(HomeFragmentDirections.globalAuthAction())
            }
        }
    }
}
