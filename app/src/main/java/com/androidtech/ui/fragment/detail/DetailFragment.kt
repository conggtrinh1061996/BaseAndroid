package com.androidtech.ui.fragment.detail

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar
import androidx.activity.addCallback
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.androidtech.base.BaseFragment
import com.androidtech.base.R
import com.androidtech.base.databinding.FragmentDetailBinding
import com.androidtech.ui.activity.MainViewModel
import com.androidtech.util.Logger
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import kotlin.getValue

@AndroidEntryPoint
class DetailFragment : BaseFragment<FragmentDetailBinding>(FragmentDetailBinding::inflate) {
    private val mainViewModel: MainViewModel by activityViewModels()
    private val args: DetailFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupWebView()
        handleBackPress()

        try {
            val urlFromArgs = args.url
            if(urlFromArgs.isNotEmpty()) {
                mainViewModel.setCurrentUrl(urlFromArgs)
            }
        } catch (e: Exception) {
            Logger.d("Exception: ${e.message}")
        }


        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                mainViewModel.uiState.collect { mainState ->
                    mainState.currentUrl?.let { url->
                        if(binding.wvNews.url != url) {
                            binding.wvNews.loadUrl(url)
                        }
                    }
                }
            }
        }

        setupUI()

    }
    private fun setupUI() {
        binding.imgShare.setOnClickListener {
            Logger.d("Share pressed")
            shareUrl()
        }
        binding.imgBack.setOnClickListener {
            Logger.d("Back pressed")
            onBackPressed()
        }
        binding.imgSave.setOnClickListener {
            Logger.d("Save pressed")
        }
    }
    private fun onBackPressed() {
        if(binding.wvNews.canGoBack()){
            binding.wvNews.goBack()
        } else {
            findNavController().popBackStack()
        }
    }

    private fun setupWebView() {
        binding.wvNews.settings.apply {
            javaScriptEnabled = true
            domStorageEnabled = true
            loadWithOverviewMode = true
            useWideViewPort = true
        }

        binding.wvNews.webViewClient = object : WebViewClient() {
            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                binding.progressBar.isVisible = true
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                binding.progressBar.isVisible = false
            }

            override fun shouldOverrideUrlLoading(
                view: WebView?,
                request: WebResourceRequest?
            ): Boolean {
                return false
            }
        }
    }

    private fun handleBackPress() {
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            if(binding.wvNews.canGoBack()) binding.wvNews.goBack()
            else findNavController().popBackStack()
        }
    }
    private fun shareUrl() {
        val urlToShare = binding.wvNews.url ?: mainViewModel.uiState.value.currentUrl
        if(!urlToShare.isNullOrEmpty()) {
            val intent = Intent(Intent.ACTION_SEND).apply {
                type= "text/plain"
                putExtra(Intent.EXTRA_TEXT, urlToShare)
            }
            startActivity(Intent.createChooser(intent, "Share news to ...."))
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        binding.wvNews.destroy()
    }
}