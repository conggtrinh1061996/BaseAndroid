package com.androidtech.ui.fragment.auth

import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.androidtech.base.BaseFragment
import com.androidtech.base.R
import com.androidtech.base.databinding.FragmentLoginBinding
import com.androidtech.ui.activity.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {

    private val viewModel by viewModels<AuthViewModel>()
    private val mainViewModel: MainViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupEventListeners()
        observeViewModel()

    }
    private fun setupEventListeners() {
        binding.btnLogin.setOnClickListener {
            binding.tilEmail.error = null
            binding.tilPassword.error = null


            val email = binding.etEmail.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()

            if (validateInput(email, password)) {
                viewModel.onLoginClicked(email, password)
            }
        }
        binding.tvRegister.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
    }
    private fun observeViewModel() {
        lifecycleScope.launch {
            viewModel.uiState.collect { authState ->
                if (authState.user != null) {
                    findNavController().navigate(R.id.action_loginFragment_to_newsFragment)
                    mainViewModel.checkAuthentication()
                    Toast.makeText(context, "Welcome ${authState.user.name}", Toast.LENGTH_SHORT).show()

                }else{
                    //Toast.makeText(context, "Login failed, please try again", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun validateInput(email: String, password: String): Boolean {

        if (email.isEmpty()) {
            binding.tilEmail.error = "Please enter your email"
            return false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.tilEmail.error = "Please enter a valid email"
            return false
        }
        if (password.isEmpty()) {
            binding.tilPassword.error = "Please enter your password"
            return false
        } else if (password.length < 6) {
            binding.tilPassword.error = "Password must be at least 6 characters"
            return false
        }

        return true
    }
}