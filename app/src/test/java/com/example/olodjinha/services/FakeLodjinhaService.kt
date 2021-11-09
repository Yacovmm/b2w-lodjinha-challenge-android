package com.example.olodjinha.services

import com.example.olodjinha.api.LodjinhaService
import com.example.olodjinha.models.GetBannerResponse
import com.example.olodjinha.models.GetCategoriaResponse
import com.example.olodjinha.models.ProdutoResponse
import retrofit2.Response

class FakeLodjinhaService : LodjinhaService {

    var shouldApiReturnError = false

    var bannerList = listOf(
        GetBannerResponse.Banner(
            id = "1",
            linkUrl = "url1",
            urlImagem = "urlIma"
        ),
    )

    var categorieList = listOf(
        GetCategoriaResponse.Categoria(
            id = 1,
            descricao = "descr",
            urlImagem = "url"
        )
    )

    var productsList = listOf(
        ProdutoResponse.Produto(
            id = 1,
            descricao = "descr",
            urlImagem = "url",
            categoria = categorieList[0],
            precoPor = 10.0
        ),
    )

    override suspend fun getBanner(): Response<GetBannerResponse> {
        if (shouldApiReturnError)
            return Response.error(404, null)
        return Response.success(
            GetBannerResponse(data = bannerList)
        )
    }

    override suspend fun getCategoria(): Response<GetCategoriaResponse> {
        if (shouldApiReturnError)
            return Response.error(404, null)
        return Response.success(
            GetCategoriaResponse(data = categorieList)
        )
    }

    override suspend fun getMaisVendidos(): Response<ProdutoResponse> {
        if (shouldApiReturnError)
            return Response.error(404, null)
        return Response.success(
            ProdutoResponse(data = productsList)
        )
    }

    override suspend fun getProduto(
        offset: Int?,
        limit: Int?,
        categoriaId: Int?
    ): Response<ProdutoResponse> {
        if (shouldApiReturnError)
            return Response.error(404, null)
        return Response.success(
            ProdutoResponse(data = productsList)
        )
    }
}