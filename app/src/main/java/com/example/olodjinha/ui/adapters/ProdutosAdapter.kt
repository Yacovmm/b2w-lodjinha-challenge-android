package com.example.olodjinha.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.request.ImageRequest
import com.example.olodjinha.R
import com.example.olodjinha.databinding.MaisvendidosLayoutRvBinding
import com.example.olodjinha.models.ProdutoResponse

class ProdutosAdapter :
    ListAdapter<ProdutoResponse.Produto, ProdutosAdapter.MaisVendidosViewHolder>(DIFFER_CALLBACK) {

    inner class MaisVendidosViewHolder(
        private val binding: MaisvendidosLayoutRvBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(model: ProdutoResponse.Produto) {
            binding.apply {
                productIv.load(model.urlImagem) {
                    crossfade(true)
                    placeholder(R.drawable.ic_categories)
                    error(R.drawable.ic_categories)

                    listener(onSuccess = { _, _ ->
                        // do something
                    }, onError = { request: ImageRequest, throwable: Throwable ->
                        // handle error here
                        println(throwable.message)
                    })
                }

                descricaoProduto.text = model.descricao

                dePriceProduct.text = "${model.precoDe}"
                porPriceProduct.text = "${model.precoPor}"

                root.setOnClickListener {
                    onItemClickListener?.invoke(model)
                }
            }
        }
    }

    companion object {

        private val DIFFER_CALLBACK: DiffUtil.ItemCallback<ProdutoResponse.Produto> =
            object : DiffUtil.ItemCallback<ProdutoResponse.Produto>() {
                override fun areItemsTheSame(
                    oldItem: ProdutoResponse.Produto,
                    newItem: ProdutoResponse.Produto
                ): Boolean {
                    return oldItem == newItem
                }

                override fun areContentsTheSame(
                    oldItem: ProdutoResponse.Produto,
                    newItem: ProdutoResponse.Produto
                ): Boolean {
                    return oldItem.id == newItem.id
                }
            }
    }

    val differ = AsyncListDiffer(this, DIFFER_CALLBACK)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MaisVendidosViewHolder {
        val binding = MaisvendidosLayoutRvBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return MaisVendidosViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MaisVendidosViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    private var onItemClickListener: ((ProdutoResponse.Produto) -> Unit)? = null

    fun setOnItemClickListener(listener: (ProdutoResponse.Produto) -> Unit) {
        onItemClickListener = listener
    }
}
