package com.example.olodjinha.ui.fragments

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.olodjinha.models.GetBannerResponse
import com.example.olodjinha.models.GetCategoriaResponse
import com.example.olodjinha.models.ProdutoResponse
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

    private val _categoriesLiveData = MutableLiveData<List<GetCategoriaResponse.Categoria>>()
    val categoriesLiveData: LiveData<List<GetCategoriaResponse.Categoria>>
        get() = _categoriesLiveData


    fun getCategories() = viewModelScope.launch {
        val response = repository.getCategories()

        if (response.isSuccessful) {
            response.body()?.let {
                _categoriesLiveData.postValue(it.data)
            }
        }
    }

    private val _maisVendidosLiveData = MutableLiveData<List<ProdutoResponse.Produto>>()
    val maisVendidosLiveData: LiveData<List<ProdutoResponse.Produto>>
        get() = _maisVendidosLiveData

    fun getMaisVendidos() = viewModelScope.launch {
        val response = repository.getMaisVendidos()

        if (response.isSuccessful) {
            response.body()?.let {
                _maisVendidosLiveData.postValue(it.data)
            }
        }

    }

    private val _productsLiveData = MutableLiveData<List<ProdutoResponse.Produto>>()
    val productsLiveData: LiveData<List<ProdutoResponse.Produto>>
        get() = _productsLiveData

    fun getProdutos(categoriaId: Int, limit: Int?, offset: Int?) = viewModelScope.launch {
        val productsResponse = repository.getProdutos(
            limit, offset, categoriaId
        )

        if (productsResponse.isSuccessful) {
            productsResponse.body()?.let {
                _productsLiveData.postValue(it.data)
            }
        }
    }
}
