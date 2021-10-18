package com.example.olodjinha.api

import com.example.olodjinha.models.GetBannerResponse
import com.example.olodjinha.models.GetCategoriaResponse
import com.example.olodjinha.models.ProdutoResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface LodjinhaService {

    @GET("banner")
    suspend fun getBanner(): Response<GetBannerResponse>

    @GET("categoria")
    suspend fun getCategoria(): Response<GetCategoriaResponse>

    @GET("produto/maisvendidos")
    suspend fun getMaisVendidos(): Response<ProdutoResponse>

    @GET("produto")
    suspend fun getProduto(
        @Query("offset") offset: Int? = null,
        @Query("limit") limit: Int? = null,
        @Query("categoriaId") categoriaId: Int? = null
    ): Response<ProdutoResponse>

}
