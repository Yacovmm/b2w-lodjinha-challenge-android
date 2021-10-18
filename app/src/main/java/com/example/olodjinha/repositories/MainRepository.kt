package com.example.olodjinha.repositories

import com.example.olodjinha.api.LodjinhaService
import com.example.olodjinha.api.SafeApiCall
import com.example.olodjinha.models.GetBannerResponse
import com.example.olodjinha.models.GetCategoriaResponse
import com.example.olodjinha.models.ProdutoResponse
import retrofit2.Response

class MainRepository(private val service: LodjinhaService) {

    suspend fun getBanner(): Response<GetBannerResponse> {
        return SafeApiCall.safeNetworkRequest {
            service.getBanner()
        } ?: Response.success(GetBannerResponse(emptyList()))
    }

    suspend fun getCategories(): Response<GetCategoriaResponse> {
        return SafeApiCall.safeNetworkRequest {
            service.getCategoria()
        } ?: Response.success(GetCategoriaResponse(emptyList()))
    }

    suspend fun getMaisVendidos(): Response<ProdutoResponse> {
        return SafeApiCall.safeNetworkRequest {
            service.getMaisVendidos()
        } ?: Response.success(null)
    }

    suspend fun getProdutos(
        limit: Int? = null,
        offset: Int? = null,
        categoriaId: Int? = null
    ): Response<ProdutoResponse> {
        return SafeApiCall.safeNetworkRequest {
            service.getProduto(
                offset = offset,
                limit = limit,
                categoriaId = categoriaId
            )
        } ?: Response.success(null)
    }
}
