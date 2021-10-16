package com.example.olodjinha.api

import com.example.olodjinha.models.GetBannerResponse
import retrofit2.Response
import retrofit2.http.GET

interface LodjinhaService {

    @GET("banner")
    suspend fun getBanner(): Response<GetBannerResponse>
}
