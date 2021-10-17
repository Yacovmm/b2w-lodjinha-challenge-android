package com.example.olodjinha.api

import com.example.olodjinha.models.GetBannerResponse
import com.example.olodjinha.models.GetCategoriaResponse
import com.example.olodjinha.models.GetMaisVendidosResponse
import retrofit2.Response
import retrofit2.http.GET

interface LodjinhaService {

    @GET("banner")
    suspend fun getBanner(): Response<GetBannerResponse>

    @GET("categoria")
    suspend fun getCategoria(): Response<GetCategoriaResponse>

    @GET("produto/maisvendidos")
    suspend fun getMaisVendidos(): Response<GetMaisVendidosResponse>

}
