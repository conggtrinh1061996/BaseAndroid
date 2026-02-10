package com.androidtech.ui.fragment.news

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.androidtech.base.BaseFragment
import com.androidtech.base.R
import com.androidtech.base.databinding.FragmentNewsBinding
import com.androidtech.ui.activity.MainViewModel
import com.androidtech.util.Logger
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

@AndroidEntryPoint
class NewsFragment : BaseFragment<FragmentNewsBinding>(FragmentNewsBinding::inflate) {
    private val viewModel by viewModels<NewsViewModel>()
    private val mainViewModel: MainViewModel by activityViewModels()
    private lateinit var newsAdapter: ArticlePagingAdapter

    private var searchJob: Job? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setupTabLayout()
        setupSearchView()


        /*newsAdapter = ArticlePagingAdapter { url->
            findNavController().navigate(
                NewsFragmentDirections.actionNewsFragmentToDetailFragment(url)
            )
        }*/


        observePagingData()

        binding.swipe.setOnRefreshListener { newsAdapter.refresh() }

        observeLoadState()
        observeBookmarkChanges()

    }

    private fun setupRecyclerView() {
        newsAdapter = ArticlePagingAdapter(
            onArticleClick = {article ->
                val action = NewsFragmentDirections.actionNewsFragmentToDetailFragment(article.url)
                findNavController().navigate(action)
            },
            onBookmarkClick = {article, position ->
                mainViewModel.toggleBookmark(article)
                newsAdapter.notifyItemChanged(position)
            },
            isBookmarked = {article ->
                mainViewModel.uiState.value.bookmarks.any { it.url == article.url }
            }
        )
        binding.rvNews.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(requireContext()    )
        }
    }
    private fun observeBookmarkChanges() {

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                mainViewModel.uiState
                    .map { it.isInitialDataLoaded }
                    .distinctUntilChanged()
                    .collect { isReady ->
                        if(isReady) {
                            observePagingData()
                        }
                    }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                mainViewModel.uiState
                    .map { it.bookmarks }
                    .distinctUntilChanged()
                    .collect { newsAdapter.notifyDataSetChanged() }
            }
        }
    }

    private fun observePagingData(){
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.newsPagingFlow.collectLatest {
                    Logger.d("PagingData emitted")
                    newsAdapter.submitData(it)
                }
            }
        }
    }
    private fun observeLoadState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                newsAdapter.loadStateFlow.collectLatest { loadStates ->
                    binding.swipe.isRefreshing = loadStates.refresh is LoadState.Loading
                }
            }
        }
    }

    private fun submitData() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.newsPagingFlow.collectLatest {
                    Logger.d("PagingData emitted")
                    newsAdapter.submitData(it)
                }
            }
        }
        newsAdapter.addLoadStateListener { loadStates ->
            val isLoading = loadStates.refresh is LoadState.Loading
            val isError = loadStates.refresh is LoadState.Error

            Logger.d("state = $loadStates")
        }
    }

    private fun setupTabLayout() {
        val categories = listOf("general", "business", "entertainment", "health", "science", "sports", "technology")
        for (category in categories) {
            val tab = binding.tabLayout.newTab()
            tab.text = category
            binding.tabLayout.addTab(tab)
        }
        val savedCategory = mainViewModel.uiState.value.selectedCategory
        val saveIndex = categories.indexOf(savedCategory).coerceAtLeast(0)

        binding.tabLayout.getTabAt(saveIndex)?.select()

        binding.tabLayout.addOnTabSelectedListener(
            object: TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab) {
                    binding.searchView.setQuery("", false)
                    viewModel.setSearchQuery("")
                    val category = categories[tab.position]
                    mainViewModel.setSelectedCategory(category)
                    viewModel.selectCategory(category)
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {
                }

                override fun onTabReselected(tab: TabLayout.Tab?) {
                }

            }
        )
    }
    private fun setupSearchView() {

        binding.searchView.setOnQueryTextListener(
            object: androidx.appcompat.widget.SearchView.OnQueryTextListener{
                override fun onQueryTextSubmit(query: String?): Boolean {
                    query?.let { viewModel.setSearchQuery(it) }
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    searchJob?.cancel()
                    searchJob = lifecycleScope.launch {
                        delay(500)
                        viewModel.setSearchQuery(newText.orEmpty())
                    }
                    return true
                }

            }
        )
    }
}