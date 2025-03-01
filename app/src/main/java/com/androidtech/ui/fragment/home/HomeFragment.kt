package com.androidtech.ui.fragment.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.androidtech.base.BaseFragment
import com.androidtech.base.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment: BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    private val viewModel by viewModels<HomeViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.fetchDemo()

        lifecycleScope.launch {
            viewModel.uiState.collect { homeState ->
                binding.apply {
                    demoText.text = if (homeState.demo != null) homeState.demo.description else homeState.errorMessage
                }
            }
        }

    }

}