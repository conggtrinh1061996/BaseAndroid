package com.androidtech.ui.fragment.auth

import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.androidtech.base.BaseFragment
import com.androidtech.base.R
import com.androidtech.base.databinding.FragmentRegisterBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RegisterFragment : BaseFragment<FragmentRegisterBinding>(FragmentRegisterBinding::inflate) {
    private val viewModel by viewModels<AuthViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupEventListeners()
        observeViewModel()
    }

    private fun setupEventListeners() {
        binding.btnRegister.setOnClickListener {
            binding.tiName.error = null
            binding.tiEmail.error = null
            binding.tilPassword.error = null
            binding.ticonfirmlPassword.error = null


            val name = binding.etName.text.toString().trim()
            val email = binding.etEmail.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()
            val confirmPassword = binding.etConfirmPassword.text.toString().trim()

            if (validateInput(email, password,confirmPassword, name)) {
                viewModel.onRegisterClicked(email, password, name)
            }
        }
        binding.tvAlreadyHaveAccount.setOnClickListener {
            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            viewModel.uiState.collect { authState ->
                if (authState.user != null) {
                    Toast.makeText(context, "Register success and login now", Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
                }else{
                    //Toast.makeText(context, "Register failed, please try again", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun validateInput(email: String, password: String,confirmPassword: String, name: String): Boolean {

        if (email.isEmpty()) {
            binding.tiEmail.error = "Please enter your email"
            return false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.tiEmail.error = "Please enter a valid email"
            return false
        }
        if (password.isEmpty()) {
            binding.tilPassword.error = "Please enter your password"
            return false
        } else if (password.length < 6) {
            binding.tilPassword.error = "Your password must be at least 6 characters"
            return false
        }
        if (confirmPassword.isEmpty()) {
            binding.ticonfirmlPassword.error = "Please enter confirm your password"
            return false
        } else if (password != confirmPassword) {
            binding.ticonfirmlPassword.error = "Your password and confirm password must be the same"
            binding.tilPassword.error = "Your password and confirm password must be the same"
            return false
        }
        if(name.isEmpty()) {
            binding.tiName.error = "Please enter your name"
            return false
        }
        return true
    }
    /*private fun showLoading(isLoading: Boolean) {
        binding.progressBar.isVisible = isLoading
        binding.btnRegister.isEnabled = !isLoading
    }*/
}