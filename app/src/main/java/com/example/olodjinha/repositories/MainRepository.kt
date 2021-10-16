package com.example.olodjinha.repositories

import com.example.olodjinha.api.LodjinhaService
import com.example.olodjinha.models.GetBannerResponse
import retrofit2.Response

class MainRepository(private val service: LodjinhaService) {

    suspend fun getBanner(): Response<GetBannerResponse> {
        return service.getBanner()
    }
}
