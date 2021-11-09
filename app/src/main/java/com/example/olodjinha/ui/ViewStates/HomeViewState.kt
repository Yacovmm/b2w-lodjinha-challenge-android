package com.example.olodjinha.ui.ViewStates

import com.example.olodjinha.models.GetBannerResponse
import com.example.olodjinha.models.GetCategoriaResponse
import com.example.olodjinha.models.ProdutoResponse

data class HomeViewState(
    var loading: Boolean = false,
    val data: HomeData? = null,
    var error: Boolean = false
)

data class HomeData(
    val bannerData: List<GetBannerResponse.Banner>?,
    val categoriaData: List<GetCategoriaResponse.Categoria>?,
    val maisVendidosData: List<ProdutoResponse.Produto>?
)