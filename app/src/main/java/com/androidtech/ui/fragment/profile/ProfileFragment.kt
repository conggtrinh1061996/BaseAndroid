package com.androidtech.ui.fragment.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.androidtech.base.BaseFragment
import com.androidtech.base.R
import com.androidtech.base.databinding.FragmentProfileBinding
import com.androidtech.ui.activity.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding>(FragmentProfileBinding::inflate) {

    private val mainViewModel: MainViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeUser()
        binding.btnLogout.setOnClickListener {
            mainViewModel.signOut()
            findNavController().navigate(R.id.action_profileFragment_to_splashFragment)
        }
        binding.tvBookmark.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_bookmarkFragment)
        }
    }

    private fun observeUser() {
        viewLifecycleOwner.lifecycleScope.launch {
            mainViewModel.uiState.collect { mainState ->
                mainState.user.let {
                    binding.tvName.text = "Name: ${it?.name}"
                    binding.tvEmail.text = "Email: ${it?.email}"
                }
            }
        }
    }

}