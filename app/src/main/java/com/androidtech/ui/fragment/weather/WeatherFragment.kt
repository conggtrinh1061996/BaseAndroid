package com.androidtech.ui.fragment.weather

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.androidtech.base.databinding.FragmentWeatherBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class WeatherFragment: Fragment() {
    lateinit var binding: FragmentWeatherBinding
    private val viewModel by viewModels<WeatherViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWeatherBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launch {
            viewModel.uiState.collect {
                it.weatherModel?.let { weatherState ->
                    binding.tvCity.text = weatherState.name
                    binding.tvTemperature.text = weatherState.main?.temp.toString()
                    binding.city.text = weatherState.name
                    binding.tvCountry.text = weatherState.sys?.country
                }
            }
        }

        binding.ivSearch.setOnClickListener {
            if (binding.edtCity.text.toString().isNotEmpty())
                viewModel.fetchWeather(binding.edtCity.text.toString())
        }

    }
}