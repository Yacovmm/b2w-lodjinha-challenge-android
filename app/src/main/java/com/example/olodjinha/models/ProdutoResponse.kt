package com.example.olodjinha.models

data class ProdutoResponse(
    val data: List<Produto>,
    val offset: Int = 0,
    val total: Int = 0
) {

    data class Produto(
        val categoria: GetCategoriaResponse.Categoria,
        val descricao: String = "",
        val id: Int,
        val nome: String = "",
        val precoDe: Double = 0.0,
        val precoPor: Double = 0.0,
        val urlImagem: String = ""
    )

}
