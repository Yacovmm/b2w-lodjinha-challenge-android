package com.example.olodjinha.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import coil.load
import coil.request.ImageRequest
import coil.transform.CircleCropTransformation
import com.example.olodjinha.R
import com.example.olodjinha.databinding.BannerLayoutRvBinding
import com.example.olodjinha.databinding.CategoriesLayoutRvBinding
import com.example.olodjinha.models.GetBannerResponse
import com.example.olodjinha.models.GetCategoriaResponse
import com.example.olodjinha.ui.fragments.MainFragmentDirections

class CategoriesAdapter :
    ListAdapter<GetCategoriaResponse.Categoria, CategoriesAdapter.CategoriaViewHolder>(differCallback) {

    inner class CategoriaViewHolder(
        private val binding: CategoriesLayoutRvBinding,
        val context: Context
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(model: GetCategoriaResponse.Categoria) {

            println(model.urlImagem)

            binding.apply {
                categoryIv.load(model.urlImagem) {
                    crossfade(true)
                    transformations(CircleCropTransformation())
                    placeholder(R.drawable.ic_categories)
                    error(R.drawable.ic_categories)

                    listener(onSuccess = { _, _ ->
                        // do something
                    }, onError = { request: ImageRequest, throwable: Throwable ->
                        // handle error here
                        println(throwable.message)
                    })
                }

                categorieTitleText.text = model.descricao

                root.setOnClickListener {
                    onItemClickListener?.invoke(model)
                }
            }
        }
    }

    companion object {

        private val differCallback: DiffUtil.ItemCallback<GetCategoriaResponse.Categoria> =
            object : DiffUtil.ItemCallback<GetCategoriaResponse.Categoria>() {
                override fun areItemsTheSame(
                    oldItem: GetCategoriaResponse.Categoria,
                    newItem: GetCategoriaResponse.Categoria
                ): Boolean {
                    return oldItem == newItem
                }

                override fun areContentsTheSame(
                    oldItem: GetCategoriaResponse.Categoria,
                    newItem: GetCategoriaResponse.Categoria
                ): Boolean {
                    return oldItem.id == newItem.id
                }
            }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriaViewHolder {
        val binding = CategoriesLayoutRvBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return CategoriaViewHolder(binding, parent.context)
    }

    override fun onBindViewHolder(holder: CategoriaViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    private var onItemClickListener: ((GetCategoriaResponse.Categoria) -> Unit)? = null

    fun setOnItemClickListener(listener: (GetCategoriaResponse.Categoria) -> Unit) {
        onItemClickListener = listener
    }
}
