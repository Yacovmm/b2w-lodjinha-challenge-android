package com.example.olodjinha.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.request.ImageRequest
import coil.transform.CircleCropTransformation
import com.example.olodjinha.R
import com.example.olodjinha.databinding.MaisvendidosLayoutRvBinding
import com.example.olodjinha.models.GetMaisVendidosResponse

class MaisVendidosAdapter :
    ListAdapter<GetMaisVendidosResponse.MaisVendidos, MaisVendidosAdapter.MaisVendidosViewHolder>(differCallback) {

    inner class MaisVendidosViewHolder(
        private val binding: MaisvendidosLayoutRvBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(model: GetMaisVendidosResponse.MaisVendidos) {
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

        private val differCallback: DiffUtil.ItemCallback<GetMaisVendidosResponse.MaisVendidos> =
            object : DiffUtil.ItemCallback<GetMaisVendidosResponse.MaisVendidos>() {
                override fun areItemsTheSame(
                    oldItem: GetMaisVendidosResponse.MaisVendidos,
                    newItem: GetMaisVendidosResponse.MaisVendidos
                ): Boolean {
                    return oldItem == newItem
                }

                override fun areContentsTheSame(
                    oldItem: GetMaisVendidosResponse.MaisVendidos,
                    newItem: GetMaisVendidosResponse.MaisVendidos
                ): Boolean {
                    return oldItem.id == newItem.id
                }
            }
    }

    val differ = AsyncListDiffer(this, differCallback)

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

    private var onItemClickListener: ((GetMaisVendidosResponse.MaisVendidos) -> Unit)? = null

    fun setOnItemClickListener(listener: (GetMaisVendidosResponse.MaisVendidos) -> Unit) {
        onItemClickListener = listener
    }
}
