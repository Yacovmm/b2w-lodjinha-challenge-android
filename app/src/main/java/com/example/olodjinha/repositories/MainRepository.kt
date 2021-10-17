package com.example.olodjinha.repositories

import com.example.olodjinha.api.LodjinhaService
import com.example.olodjinha.models.GetBannerResponse
import com.example.olodjinha.models.GetCategoriaResponse
import com.example.olodjinha.models.GetMaisVendidosResponse
import retrofit2.Response

class MainRepository(private val service: LodjinhaService) {

    suspend fun getBanner(): Response<GetBannerResponse> {
        return service.getBanner()
    }

    suspend fun getCategories(): Response<GetCategoriaResponse> {
        return service.getCategoria()
    }

    suspend fun getMaisVendidos(): Response<GetMaisVendidosResponse> {
        return service.getMaisVendidos()
    }
}
