package com.androidtech.ui.fragment.bookmark

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.androidtech.base.BaseFragment
import com.androidtech.base.R
import com.androidtech.base.databinding.FragmentBookmarkBinding
import com.androidtech.ui.activity.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import kotlin.getValue

@AndroidEntryPoint
class BookmarkFragment : BaseFragment<FragmentBookmarkBinding>(FragmentBookmarkBinding::inflate) {
    private val mainViewModel: MainViewModel by activityViewModels()
    private lateinit var bookmarkAdapter: BookmarkAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        observeBookmarks()
        setupUi()
    }

    private fun setupRecyclerView() {
        bookmarkAdapter = BookmarkAdapter(
            onDeleteClick = {article ->
                mainViewModel.toggleBookmark(article)
            }
        )
        binding.rvBookmark.apply {
            adapter = bookmarkAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }
    private fun observeBookmarks() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                mainViewModel.uiState.collect { state ->
                    bookmarkAdapter.setData(state.bookmarks)
                }
            }
        }
    }
    private fun setupUi() {
        binding.imgBack.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.swipe.setOnRefreshListener {
            mainViewModel.observeBookmarks()
            binding.swipe.isRefreshing = false
        }
    }
}