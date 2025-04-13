package com.androidtech.ui.fragment.mars_photo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.androidtech.base.databinding.FragmentMarsPhotoBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PhotoFragment: Fragment() {
    private lateinit var binding: FragmentMarsPhotoBinding
    private lateinit var photoAdapter: PhotoAdapter
    private val photoViewModel by viewModels<PhotoViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMarsPhotoBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.rvPhotos.apply {
            photoAdapter = PhotoAdapter()
            adapter = photoAdapter
        }

        photoViewModel.fetchPhoto()

        lifecycleScope.launch {
            photoViewModel.uiState.collect { photoState ->
                photoAdapter.setData(photoState.photoList ?: listOf())
            }
        }

    }
}