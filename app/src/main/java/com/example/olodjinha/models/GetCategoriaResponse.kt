package com.example.olodjinha.models

data class GetCategoriaResponse(
    val data: List<Categoria>
) {
    data class Categoria(
        val id: Int,
        val descricao: String = "",
        val urlImagem: String = ""
    )
}
