package com.androidtech.ui.activity

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.androidtech.base.R
import com.androidtech.base.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel by viewModels<MainViewModel>()
    private lateinit var binding: ActivityMainBinding

    private var isNavInitialized = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupNavigationListener()
        callApis()
        handleObserver()
        //setUpBottomNav()
    }

    private fun callApis() {
        viewModel.getDemo()
    }

    private fun setupNavigationListener() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
        val navController = navHostFragment.navController

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.loginFragment, R.id.registerFragment -> {
                    binding.bottonNav.isVisible = false
                }
                else -> {
                    binding.bottonNav.isVisible = true
                    setUpBottomNav()
                }
            }
        }
    }

    private fun handleObserver() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
        val navController = navHostFragment.navController

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { mainState ->
                    val currentDest = navController.currentDestination?.id ?: return@collect

                    try {
                        if (mainState.isLoggedIn) {

                            if (currentDest == R.id.loginFragment || currentDest == R.id.registerFragment) {
                                navController.navigate(R.id.newsFragment) {
                                    popUpTo(navController.graph.startDestinationId) { inclusive = true }
                                    launchSingleTop = true
                                }
                            }
                        } else {
                            binding.bottonNav.isVisible = false
                            if (currentDest != R.id.loginFragment && currentDest != R.id.registerFragment) {
                                navController.navigate(R.id.loginFragment) {
                                    popUpTo(navController.graph.id) { inclusive = true }
                                    launchSingleTop = true
                                }
                            }
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }
        }
    }
    private fun setUpBottomNav() {
        if (isNavInitialized) return
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
        val navController: NavController = navHostFragment.navController
        binding.bottonNav.setupWithNavController(navController)
        binding.bottonNav.setOnItemSelectedListener {
            when(it.itemId) {
                R.id.newsFragment -> {
                    when (navController.currentDestination?.id) {
                        R.id.detailFragment -> {
                            navController.popBackStack(R.id.newsFragment, false)
                            true
                        }
                        R.id.searchFragment -> {
                            navController.popBackStack(R.id.newsFragment, false)
                            true
                        }
                        R.id.profileFragment -> {
                            navController.popBackStack(R.id.newsFragment, false)
                            true
                        }
                        R.id.newsFragment -> {
                            false
                        }
                        else -> {
                            navController.navigate(R.id.newsFragment)
                            true
                        }
                    }
                }
                R.id.searchFragment -> {
                    if(navController.currentDestination?.id == R.id.searchFragment) {
                        false
                    } else {
                        navController.navigate(R.id.searchFragment)
                        true
                    }
                }
                R.id.detailFragment -> {
                    if (navController.currentDestination?.id == R.id.detailFragment) {
                        false
                    } else {
                        val currentUrl = viewModel.uiState.value.currentUrl
                        if(currentUrl.isNullOrEmpty()){
                            Toast.makeText(this, "Please choose news first", Toast.LENGTH_SHORT).show()
                            false
                        } else {
                            navController.navigate(R.id.detailFragment)
                            true
                        }
                    }
                }
                R.id.profileFragment -> {
                    if(navController.currentDestination?.id == R.id.profileFragment) {
                        false
                    } else {
                        navController.navigate(R.id.profileFragment)
                        true
                    }
                }

                else -> false
            }
        }
        isNavInitialized = true
    }
}