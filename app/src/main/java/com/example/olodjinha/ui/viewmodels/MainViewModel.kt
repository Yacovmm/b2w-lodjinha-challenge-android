package com.example.olodjinha.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.olodjinha.models.GetBannerResponse
import com.example.olodjinha.models.GetCategoriaResponse
import com.example.olodjinha.models.ProdutoResponse
import com.example.olodjinha.repositories.MainRepository
import com.example.olodjinha.ui.ViewStates.HomeData
import com.example.olodjinha.ui.ViewStates.HomeViewState
import com.example.olodjinha.utils.ResponseWrapper
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import retrofit2.Response
import kotlin.system.measureNanoTime
import kotlin.system.measureTimeMillis
import kotlin.time.ExperimentalTime
import kotlin.time.measureTime
import kotlin.time.measureTimedValue

class MainViewModel(
    private val repository: MainRepository,
) : ViewModel() {

    private val _homeLiveData = MutableLiveData<HomeViewState>()
    val homeLiveData: LiveData<HomeViewState> get() = _homeLiveData

    fun getMainHomeData() = viewModelScope.launch {
        _homeLiveData.value = HomeViewState(loading = true)

        val time1 = measureTimeMillis {
            val bannerResponse = async { repository.getBanner() }
            val categoriaResponse = async { repository.getCategories() }
            val productsResponse = async { repository.getMaisVendidos() }
            val data = handleResponses(
                bannerResponse.await(),
                categoriaResponse.await(),
                productsResponse.await()
            )

            _homeLiveData.postValue(data)
            // 1319 ms
            // 812 ms
        }
        println("Tempo é ${time1} ms")
    }

    private fun handleResponses(
        bannerResponse: ResponseWrapper<GetBannerResponse?>,
        categoriaResponse: ResponseWrapper<GetCategoriaResponse?>,
        productsResponse: ResponseWrapper<ProdutoResponse?>
    ): HomeViewState {
        if (bannerResponse is ResponseWrapper.Success
            && categoriaResponse is ResponseWrapper.Success
            && productsResponse is ResponseWrapper.Success) {
            return HomeViewState(
                loading = false,
                data = HomeData(
                    bannerData = bannerResponse.result?.data,
                    categoriaData = categoriaResponse.result?.data,
                    maisVendidosData = productsResponse.result?.data
                )
            )
        }
        return HomeViewState(
            loading = false,
            data = null,
            error = true
        )
    }

    private val _categoriesLiveData = MutableLiveData<List<GetCategoriaResponse.Categoria>>()
    val categoriesLiveData: LiveData<List<GetCategoriaResponse.Categoria>>
        get() = _categoriesLiveData


    fun getCategories() = viewModelScope.launch {
        val response = repository.getCategories()

        if (response is ResponseWrapper.Success) {
            response.result?.let {
                _categoriesLiveData.postValue(it.data)
            }
        }
    }

    private val _maisVendidosLiveData = MutableLiveData<List<ProdutoResponse.Produto>>()
    val maisVendidosLiveData: LiveData<List<ProdutoResponse.Produto>>
        get() = _maisVendidosLiveData

    fun getMaisVendidos() = viewModelScope.launch {
        val response = repository.getMaisVendidos()

        if (response is ResponseWrapper.Success) {
            response.result?.let {
                _maisVendidosLiveData.postValue(it.data)
            }
        }

    }

    private val _productsLiveData = MutableLiveData<List<ProdutoResponse.Produto>>()
    val productsLiveData: LiveData<List<ProdutoResponse.Produto>>
        get() = _productsLiveData

    //
    var pageOffset = 0
    var pageLimit = 20
    var totalItemsFromApi = 0
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

                // Add all the products to already existed list
                productsList.addAll(it.data)

                // Set the total items
                totalItemsFromApi = it.total


                pageOffset = pageLimit
                pageLimit += 20

                if (pageLimit > totalItemsFromApi)
                    pageLimit = totalItemsFromApi

                _productsLiveData.postValue(productsList)
            }
        }
    }

    var hasMoreItems = true
}

