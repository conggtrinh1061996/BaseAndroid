package com.androidtech.ui.fragment.user

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.androidtech.base.databinding.FragmentUserBinding
import com.androidtech.base.databinding.FragmentWeatherBinding
import com.androidtech.data.model.user.UserModel
import com.androidtech.domain.model.user.User
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class UserFragment: Fragment() {
    lateinit var binding: FragmentUserBinding
    private val userViewModel by viewModels<UserViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUserBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnGet.setOnClickListener {
            userViewModel.getUser()
            Log.d("t", userViewModel.toString())
        }

        binding.btnInsert.setOnClickListener {
            lifecycleScope.launch {
                try {
                    userViewModel.insertUser(User(binding.edtId.text.toString().toInt(), binding.edtName.text.toString()))
                } catch (_: Exception) {

                }
            }
        }

        binding.btnDelete.setOnClickListener {
            lifecycleScope.launch {
                userViewModel.deleteUser()
            }
        }

        binding.btnFind.setOnClickListener {
            try {
                userViewModel.findUserById(binding.edtId.text.toString().toInt())
            } catch (_: Exception) {

            }
        }

        lifecycleScope.launch {
            userViewModel.uiState.collect { state ->
                val sb = StringBuilder()
                state.userList.forEach { user ->
                    sb.append(user).append("\n")
                }

                binding.tvShow.text = sb.toString()
            }
        }
    }
}