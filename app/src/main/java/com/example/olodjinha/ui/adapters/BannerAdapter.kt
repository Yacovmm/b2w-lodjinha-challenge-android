package com.example.olodjinha.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.olodjinha.databinding.BannerLayoutRvBinding
import com.example.olodjinha.models.GetBannerResponse
import com.example.olodjinha.ui.fragments.MainFragmentDirections

class BannerAdapter :
    ListAdapter<GetBannerResponse.Banner, BannerAdapter.BannerViewHolder>(differCallback) {

    inner class BannerViewHolder(
        private val binding: BannerLayoutRvBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(banner: GetBannerResponse.Banner) {
            binding.image.apply {
//                ViewCompat.setTransitionName(this, "imagem")

                load(banner.urlImagem)
                setOnClickListener {
                    onItemClickListener?.invoke(banner)
                    val extras = FragmentNavigatorExtras(
                        this to "imagem_teste"
                    )
                    findNavController().navigate(
                        MainFragmentDirections.actionMainFragmentToSecondFragment(
                            banner.urlImagem
                        ),
                        extras
                    )
                }
            }
        }
    }

    companion object {

        private val differCallback: DiffUtil.ItemCallback<GetBannerResponse.Banner> =
            object : DiffUtil.ItemCallback<GetBannerResponse.Banner>() {
                override fun areItemsTheSame(
                    oldItem: GetBannerResponse.Banner,
                    newItem: GetBannerResponse.Banner
                ): Boolean {
                    return oldItem == newItem
                }

                override fun areContentsTheSame(
                    oldItem: GetBannerResponse.Banner,
                    newItem: GetBannerResponse.Banner
                ): Boolean {
                    return oldItem.id == newItem.id
                }
            }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BannerViewHolder {
        val binding = BannerLayoutRvBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return BannerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BannerViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    private var onItemClickListener: ((GetBannerResponse.Banner) -> Unit)? = null

    fun setOnItemClickListener(listener: (GetBannerResponse.Banner) -> Unit) {
        onItemClickListener = listener
    }
}
