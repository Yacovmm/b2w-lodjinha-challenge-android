package com.example.olodjinha.ui.viewmodels

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

    private val _bannerLiveData = MutableLiveData<ViewState>()
    val bannerLiveData: LiveData<ViewState> get() = _bannerLiveData

    fun getBanners() = viewModelScope.launch {
        _bannerLiveData.postValue(
            ViewState(
                loading = true
            )
        )
        val response = repository.getBanner()

        if (response.isSuccessful) {
            response.body()?.let {

                _bannerLiveData.postValue(
                    ViewState(
                        loading = false,
                        data = it.data
                    )
                )
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


    var pageOffset = 0
    var pageLimit = 20
    var totaItemsFromApi = 0
    private val productsList = mutableListOf<ProdutoResponse.Produto>()
    var isPaginating = true

    fun getProdutos(
        categoriaId: Int,
    ) = viewModelScope.launch {
        isPaginating = true
        val productsResponse = repository.getProdutos(
            categoriaId = null,
            offset = pageOffset,
            limit = pageLimit
        )

        if (productsResponse.isSuccessful) {
            productsResponse.body()?.let {
                if (it.data.isEmpty()) {
                    hasMoreItems = false
                }

                productsList.addAll(it.data)

                totaItemsFromApi = it.total

                pageOffset = pageLimit
                pageLimit += 20

                if (pageLimit > totaItemsFromApi)
                    pageLimit = totaItemsFromApi

                _productsLiveData.postValue(productsList)
            }
        }
    }

    var hasMoreItems = true
}

data class ViewState(
    var loading: Boolean = false,
    val data: List<GetBannerResponse.Banner>? = null
)
