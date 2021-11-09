package com.example.olodjinha.repositories

import com.example.olodjinha.api.LodjinhaService
import com.example.olodjinha.api.SafeApiCall
import com.example.olodjinha.models.GetBannerResponse
import com.example.olodjinha.models.GetCategoriaResponse
import com.example.olodjinha.models.ProdutoResponse
import com.example.olodjinha.utils.ResponseWrapper
import retrofit2.Response

class MainRepository(private val service: LodjinhaService) {

    suspend fun getBanner(): ResponseWrapper<GetBannerResponse?> {
        return SafeApiCall.safeNetworkRequest {
            ResponseWrapper.Success(
                service.getBanner().body()
            )
        } ?: ResponseWrapper.Error()
    }

    suspend fun getCategories(): ResponseWrapper<GetCategoriaResponse?> {
        return SafeApiCall.safeNetworkRequest {
            ResponseWrapper.Success(
                result = service.getCategoria().body()
            )
        } ?: ResponseWrapper.Error()
    }

    suspend fun getMaisVendidos(): ResponseWrapper<ProdutoResponse?> {
        return SafeApiCall.safeNetworkRequest {
            ResponseWrapper.Success(
                result = service.getMaisVendidos().body()
            )
        } ?: ResponseWrapper.Error()
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
