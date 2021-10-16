package com.example.olodjinha.ui.fragments

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.olodjinha.models.GetBannerResponse
import com.example.olodjinha.repositories.MainRepository
import kotlinx.coroutines.launch

class MainViewModel(
    private val repository: MainRepository
) : ViewModel() {

    private val _bannerLiveData = MutableLiveData<List<GetBannerResponse.Banner>>()
    val bannerLiveData: LiveData<List<GetBannerResponse.Banner>> get() = _bannerLiveData

    fun getBanners() = viewModelScope.launch {
        val response = repository.getBanner()

        if (response.isSuccessful) {
            response.body()?.let {
                _bannerLiveData.postValue(it.data)
            }
        }
    }
}
