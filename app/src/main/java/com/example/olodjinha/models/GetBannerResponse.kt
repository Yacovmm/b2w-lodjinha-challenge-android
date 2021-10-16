package com.example.olodjinha.models

data class GetBannerResponse(
    val data: List<Banner>
) {
    data class Banner(
        val id: String = "",
        val linkUrl: String = "",
        val urlImagem: String = ""
    )
}
